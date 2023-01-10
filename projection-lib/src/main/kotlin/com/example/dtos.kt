package com.example

import java.util.UUID

data class GroupId(val groupId: UUID)

data class RemoveGroupAssociations(val groupId: UUID, val profiles: Set<UUID>)

data class ExistsById(val exists: Boolean)