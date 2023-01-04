package com.exmaple.applicationservice

import abstractcom.example.applicationservice.CommentService
import com.example.event.*
import com.example.repository.CommentRepository
import org.example.EntityNotFoundException
import org.example.EventPublisher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.capture
import org.mockito.kotlin.verify
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class CommentServiceTest {
    private lateinit var commentService: CommentService
    private lateinit var commentRepository: CommentRepository
    private lateinit var eventPublisher: EventPublisher

    @BeforeEach
    fun initialise() {
        commentRepository = mock(CommentRepository::class.java)
        eventPublisher = mock(EventPublisher::class.java)
        commentService = CommentService(commentRepository, eventPublisher)
    }

    @Test
    fun `should add comment to post and save it`() {
        `when`(commentRepository.save(anyOrNull())).then { it.arguments.first() }
        `when`(commentRepository.existById(anyOrNull())).thenReturn(true)
        val captor: ArgumentCaptor<CommentCreatedEvent>  = ArgumentCaptor.forClass(CommentCreatedEvent::class.java)

        commentService.addComment(commentProjection())

//        verify(eventPublisher).publish(capture(captor))

        assertEquals("DUPA", captor.value.comment.text)
    }

    @Test
    fun `should delete comment`() {
        Mockito.doNothing().`when`(commentRepository).deleteById(anyOrNull())
        val captor: ArgumentCaptor<CommentDeletedEvent>  = ArgumentCaptor.forClass(CommentDeletedEvent::class.java)
        val uuid = UUID.randomUUID()

        commentService.deleteComment(uuid)

//        verify(eventPublisher).publish(capture(captor))

        assertEquals(uuid, captor.value.commentId)
        assertNotNull(captor.value.eventId)
    }

    @Test
    fun `should add reply succesfully`() {
        val captor: ArgumentCaptor<ReplyAddedEvent>  = ArgumentCaptor.forClass(ReplyAddedEvent::class.java)
        val captorId: ArgumentCaptor<UUID>  = ArgumentCaptor.forClass(UUID::class.java)
        val comment = comment()
        `when`(commentRepository.findById(anyOrNull())).thenReturn(comment)

        commentService.addReply(comment.commentId!!, replyCreateProjection())

//        verify(eventPublisher).publish(capture(captor))
        verify(commentRepository).findById(capture(captorId))

        assertEquals(comment.commentId, captorId.value)
        assertEquals(1, captor.value.comment.replies.size)
        assertEquals(captor.value.comment.replies.first(), captor.value.comment.replies.first())
    }

    @Test
    fun `should edit reply`() {
        val captor: ArgumentCaptor<ReplyEditedEvent>  = ArgumentCaptor.forClass(ReplyEditedEvent::class.java)
        val captorId: ArgumentCaptor<UUID>  = ArgumentCaptor.forClass(UUID::class.java)
        val comment = commentWithRelies()
        val reply = replyEdtProjection().also { it.replyId = comment.replies.first().replyId }
        `when`(commentRepository.findById(anyOrNull())).thenReturn(comment)

        commentService.editReply(comment.commentId!!, reply)

//        verify(eventPublisher).publish(capture(captor))
        verify(commentRepository).findById(capture(captorId))

        assertEquals(comment.commentId, captorId.value)
        assertEquals(1, captor.value.comment.replies.size)
        assertEquals(captor.value.comment.replies.first(), captor.value.comment.replies.first())
    }

    @Test
    fun removeReply() {
        val captor: ArgumentCaptor<RemoveReplyEvent>  = ArgumentCaptor.forClass(RemoveReplyEvent::class.java)
        val captorId: ArgumentCaptor<UUID>  = ArgumentCaptor.forClass(UUID::class.java)
        val comment = commentWithRelies()
        val reply = replyEdtProjection().also { it.replyId = comment.replies.first().replyId }
        `when`(commentRepository.findById(anyOrNull())).thenReturn(comment)

        commentService.removeReply(comment.commentId!!, reply.replyId)

//        verify(eventPublisher).publish(capture(captor))
        verify(commentRepository).findById(capture(captorId))

        assertEquals(comment.commentId, captorId.value)
        assertEquals(0, captor.value.comment.replies.size)
    }

    @Test
    fun `should rise an error when entity not found add comment`() {
        `when`(commentRepository.existById(anyOrNull())).thenReturn(false)

        assertThrows<EntityNotFoundException> { commentService.addComment(commentProjection()) }
    }

    @Test
    fun `should rise an error when entity not found editReply`() {
        `when`(commentRepository.findById(anyOrNull())).thenReturn(null)

        assertThrows<EntityNotFoundException> { commentService.editReply(UUID.randomUUID(), replyEdtProjection()) }
    }

    @Test
    fun `should rise an error when entity not found removeReply`() {
        `when`(commentRepository.findById(anyOrNull())).thenReturn(null)

        assertThrows<EntityNotFoundException> { commentService.removeReply(UUID.randomUUID(), UUID.randomUUID()) }
    }

    @Test
    fun `should rise an error when entity not found`() {
        `when`(commentRepository.findById(anyOrNull())).thenReturn(null)

        assertThrows<EntityNotFoundException> { commentService.addReply(UUID.randomUUID(), replyCreateProjection()) }
    }

}