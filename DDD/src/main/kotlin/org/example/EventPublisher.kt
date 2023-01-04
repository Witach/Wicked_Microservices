package org.example

interface EventPublisher {
    fun publish(domainEvent: DomainEvent<*>, topic: String)
}
