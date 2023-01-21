package com.example

import java.time.LocalDate
import java.util.*

data class GroupProjectionGraphQl (
    var groupInt: UUID? = null,
    var name: String? = null,
    var description: String? = null,
    var image: String? = null,
    var owner: ProfileProjection? = null,
    var profiles: List<ProfileProjection>? = null,
    var posts: List<PostProjection>? = null,
    var administrators: List<ProfileProjection>? = null
)

data class ProfileProjectionQL (
    var profileID: UUID? = null,
    var username: String? = null,
    var birthday: LocalDate? = null,
)