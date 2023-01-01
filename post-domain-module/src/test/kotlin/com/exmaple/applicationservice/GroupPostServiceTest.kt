package com.exmaple.applicationservice

import com.example.applicationservice.GroupPostService
import com.example.applicationservice.SessionStorage
import com.example.event.GroupPostAddedEvent
import com.example.repository.CommentRepository
import com.example.repository.GroupPostRepository
import com.example.repository.GroupRepository
import org.example.EventPublisher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.capture
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

class GroupPostServiceTest {
    private lateinit var groupPostRepository: GroupPostRepository
    private lateinit var commentRepository: CommentRepository
    private lateinit var groupRepository: GroupRepository
    private lateinit var sessionStorage: SessionStorage
    private lateinit var eventPublisher: EventPublisher

    private lateinit var groupPostService: GroupPostService

    @BeforeEach
    fun initialise() {
        groupPostRepository = mock()
        commentRepository = mock()
        groupRepository = mock()
        sessionStorage = mock()
        eventPublisher = mock()

        groupPostService = GroupPostService(
            groupPostRepository,
            commentRepository,
            groupRepository,
            sessionStorage,
            eventPublisher)
    }

    @Test
    fun addGroupPost() {
        `when`(groupPostRepository.save(anyOrNull())).thenAnswer { it.arguments[0] }
        val captor: ArgumentCaptor<GroupPostAddedEvent> = ArgumentCaptor.forClass(GroupPostAddedEvent::class.java)
        val post = groupPostCreateProjection()
        groupPostService.addGroupPost(post)

        verify(eventPublisher).publish(capture(captor))

        assertEquals(captor.value.post.text, post.text)
    }
}