import com.example.entity.Profile
import com.example.entity.User
import org.example.DomainEvent
import java.util.UUID

abstract class UserEvent: DomainEvent<User>()

class UserCreatedEvent(val user: User, val profile: Profile): UserEvent()

class PasswoordChangedEvent(val user: UUID): UserEvent()

class EmailChangedEvent(val email: String): UserEvent()

class UserDeletedEvent(val user: UUID): UserEvent()