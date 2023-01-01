package org.example

import java.util.*

infix fun String?.ifBlank(other: String): String {
    return if (this.isNullOrBlank()) {
        return other
    } else {
        this
    }
}

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

