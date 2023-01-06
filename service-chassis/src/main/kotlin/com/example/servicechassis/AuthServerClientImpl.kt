package com.example.servicechassis

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.AuthServerClient
import org.example.CreateUserDto
import org.example.UserFetched
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.*

//inline fun <reified T> typeReference() = object : TypeReference<T>() {}

class AuthServerClientImpl(val authProp: AuthClientProp,
                           private val authClientResponseErrorHandler: AuthClientResponseErrorHandler): AuthServerClient {

    var restTemplate: RestTemplate

    init {
        println(authProp.authCode)
        restTemplate = RestTemplateBuilder({
            it.errorHandler = authClientResponseErrorHandler
            it.interceptors.add(AuthClientInterceptor(authProp.authCode))
        }).build()
    }

    override fun addUser(createUserDto: CreateUserDto)  {
        restTemplate.postForEntity("${authProp.host}/auth/admin/realms/SocialApp/users", createUserDto, Void::class.java)
    }

    override fun fetchUserByUsername(username: String): UserFetched? {
       val x = restTemplate.getForEntity<List<UserFetched>>("${authProp.host}auth/admin/realms/SocialApp/users?username=$username")

        val asMap = ((x.body as ArrayList).toArray()[0] as LinkedHashMap<String, Any>)

        return UserFetched(
            id = asMap["id"].toString(),
            createdTimestamp = asMap["createdTimestamp"].toString(),
            username = asMap["username"].toString(),
            enabled = asMap["enabled"].toString(),
            emailVerified = asMap["emailVerified"].toString(),
            firstName = asMap["firstName"].toString(),
            lastName = asMap["lastName"].toString(),
            email = asMap["email"].toString(),
            notBefore = asMap["notBefore"].toString()
        )
    }

    override fun fetchUserByUserId(id: UUID): UserFetched? {
        return restTemplate.getForEntity<UserFetched>("${authProp.host}auth/admin/realms/SocialApp/users/${id}", UserFetched::class).body
    }

    override fun persistUser(createUserDto: CreateUserDto): UserFetched? {
        addUser(createUserDto)
        val user = fetchUserByUsername(createUserDto.username)
        enableUser(user?.id!!)
        return user
    }

    fun enableUser(id: String) {
        restTemplate.put("${authProp.host}auth/admin/realms/SocialApp/users/$id", mapOf("enabled" to true, "emailVerified" to true))
    }

    override fun updateUser(user: UserFetched): UserFetched? {
        restTemplate.put("${authProp.host}auth/admin/realms/SocialApp/users/${user.id}", user)
        return fetchUserByUsername(user.username!!)
    }

    override fun deleteUserById(id: UUID) {
        restTemplate.delete("${authProp.host}auth/admin/realms/SocialApp/users")
    }
}


class AuthClientResponseErrorHandler(val objectMapper: ObjectMapper): ResponseErrorHandler{
    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode.isError
    }

    override fun handleError(response: ClientHttpResponse) {
        val error = objectMapper.readValue(response.body, ErrorAuthClientResponse::class.java)
        throw AuthClientException(response.statusCode, error)
    }
}

class AuthClientException(errorCode: HttpStatus, response: ErrorAuthClientResponse):
    RuntimeException("Auth Server has replied with ${errorCode.value()} status and message: ${response.error}")


data class ErrorAuthClientResponse(val error: String)

class AuthClientInterceptor(val authCode: String): ClientHttpRequestInterceptor {
    override fun intercept(request: HttpRequest, body: ByteArray,
                           execution: ClientHttpRequestExecution): ClientHttpResponse {

        request.headers.add("Authorization", "Bearer $authCode")
        request.headers.add("Content-Type", "application/json")

        return execution.execute(request, body);
    }
}