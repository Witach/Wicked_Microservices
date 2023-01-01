package org.example

import java.lang.RuntimeException
import java.util.UUID

class EntityDoesNotExists(message: String?): RuntimeException(message)

class LackOfPermissionsException(message: String): RuntimeException(message)

class EntityNotFoundException(message: String): RuntimeException(message) {
    constructor(entity: DDDEntity): this("Entity ${entity.javaClass.simpleName} of id=${entity.entityId} not found")
    constructor(entityClass: Class<*>, entityId: UUID): this("Entity ${entityClass.simpleName} of id=${entityId} not found")
}

class RequiredParamsNotIncludedException(params: List<String>): RuntimeException("Required params not included: $params")

class NoAuthenticationAvailableException(): RuntimeException("the Auth token was not found but is required")

class RegistrationException(message: String): RuntimeException("Regsitration filed with the message: $message")