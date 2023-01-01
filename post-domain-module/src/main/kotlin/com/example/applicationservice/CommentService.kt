package abstractcom.example.applicationservice

import com.example.CommentCreateProjection
import com.example.ReplyCreateProjection
import com.example.ReplyEdtProjection
import com.example.entity.Comment
import com.example.entity.Post
import com.example.event.CommentCreatedEvent
import com.example.event.CommentDeletedEvent
import com.example.repository.CommentRepository
import org.example.EntityNotFoundException
import org.example.EventPublisher
import java.time.LocalDateTime
import java.util.*

public class CommentService(
    private val commentRepository: CommentRepository,
    private val eventPublisher: EventPublisher,
) {
    fun addComment(comment: CommentCreateProjection) {
        if(commentRepository.existById(comment.postId)) {
            val createdComment = commentRepository.save(
                Comment(
                    commentId = UUID.randomUUID(),
                    postId = comment.postId,
                    authorId = comment.authorId,
                    text = comment.text,
                    sentTime = LocalDateTime.now()
                )
            )
            eventPublisher.publish(CommentCreatedEvent(createdComment))
        } else {
            throw  throw EntityNotFoundException(Post::class.java, comment.postId)
        }
    }

    fun deleteComment(commentId: UUID) {
        commentRepository.deleteById(commentId)
        eventPublisher.publish(CommentDeletedEvent(commentId))
    }

    fun addReply(commentId: UUID, reply: ReplyCreateProjection) {
        commentRepository.findById(commentId)?.also {
            val createEvent = it.addReply(reply)
            commentRepository.save(it)
            eventPublisher.publish(createEvent)
        } ?: throw EntityNotFoundException(Comment::class.java, commentId)
    }

    fun editReply(commentId: UUID, reply: ReplyEdtProjection) {
        commentRepository.findById(commentId)?.also {
            val event = it.editReply(reply)
            commentRepository.save(it)
            eventPublisher.publish(event)
        } ?: throw EntityNotFoundException(Comment::class.java, commentId)

    }

    fun removeReply(commentId: UUID, reply: UUID) {
        commentRepository.findById(commentId)?.also {
            val event = it.removeReply(reply)
            commentRepository.save(it)
            eventPublisher.publish(event)
        } ?: throw EntityNotFoundException(Comment::class.java, commentId)
    }
}