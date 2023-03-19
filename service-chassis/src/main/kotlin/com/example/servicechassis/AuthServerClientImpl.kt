package com.example.servicechassis

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.AuthServerClient
import org.example.CreateUserDto
import org.example.UserFetched
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.*
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.*

class AuthServerClientImpl(val authProp: AuthClientProp,
                           private val authClientResponseErrorHandler: AuthClientResponseErrorHandler): AuthServerClient {

    var restTemplate: RestTemplate = RestTemplateBuilder()
        .errorHandler(authClientResponseErrorHandler)
        .build()

    fun logIntoRealm() {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)

        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.add("grant_type", "password")
        map.add("client_id", "admin-cli")
        map.add("client_secret", "NDZhBV9OPqFMDsFwl1qKXnt0Mi0EU780")
        map.add("username", "social_app_admin")
        map.add("password", "social_app_admin")

        val entity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(map, headers)

        val response = restTemplate.exchange(
            "${authProp.host}auth/realms/SocialApp/protocol/openid-connect/token",
            HttpMethod.POST,
            entity,
            RealmLogIn::class.java
        )

        this.restTemplate = RestTemplateBuilder({
            it.errorHandler = authClientResponseErrorHandler
            it.interceptors.add(AuthClientInterceptor(response.body!!.access_token))
        }).build()
    }

    override fun addUser(createUserDto: CreateUserDto)  {
        restTemplate.postForEntity("${authProp.host}/auth/admin/realms/SocialApp/users", createUserDto, Void::class.java)
    }

    override fun fetchUserByUsername(username: String): UserFetched? {
       val x = restTemplate.getForEntity<List<UserFetched>>("${authProp.host}auth/admin/realms/SocialApp/users?username=$username")

        return extractUser(x)
    }

    fun fetchUserById(id: UUID): UserFetched? {
        val x = restTemplate.getForEntity<List<UserFetched>>("${authProp.host}auth/admin/realms/SocialApp/users/$id")

        return extractUser(x)
    }

    fun fetchUserById(id: String): UserFetched? = fetchUserById(id.toUUID())

    fun extractUser(response: ResponseEntity<*>) : UserFetched {
        val asMap = ((response.body as ArrayList<*>).toArray()[0] as LinkedHashMap<String, Any>)

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
        return fetchUserById(user.id)
    }

    override fun deleteUserById(id: UUID) {
        restTemplate.delete("${authProp.host}auth/admin/realms/SocialApp/users/$id")
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

data class RealmLogIn(
    val access_token : String,
    val expires_in : String,
    val refresh_expires_in: Int,
    val refresh_token: String,
    val token_type: String,
    @JsonProperty("not-before-policy") val notBeforePolicy: Int,
    val session_state: String,
    val scope: String
)