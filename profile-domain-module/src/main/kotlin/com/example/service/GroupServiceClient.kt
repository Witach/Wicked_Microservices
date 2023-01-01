package com.example.service

import java.util.*

interface GroupServiceClient {
    fun existsById(group: UUID): Boolean
}
