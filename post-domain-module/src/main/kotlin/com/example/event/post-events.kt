package com.example.event

import com.example.entity.GroupPost
import com.example.entity.Post
import org.example.DomainEvent
import java.util.UUID

abstract class PostEvent: DomainEvent<Post>()

class AttachmentAddedEvent(val post: Post): PostEvent()

class AttachmentRemovedEvent(val post: Post): PostEvent()

class PostEditedEvent(val post: Post): PostEvent()

class PostAddedEvent(val post: Post): PostEvent()

class PostRemovedEvent(val post: UUID): PostEvent()

class GroupPostAddedEvent(val post: GroupPost): PostEvent()

class GroupPostRemovedEvent(val post: GroupPost): PostEvent()

class GroupPostEditedEvent(val post: GroupPost): PostEvent()
