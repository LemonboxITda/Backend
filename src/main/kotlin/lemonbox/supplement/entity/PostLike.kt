package lemonbox.supplement.entity

import org.springframework.data.annotation.CreatedDate
import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@IdClass(PostLikeId::class)
class PostLike (
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @CreatedDate
    var createdAt: Instant,
)

data class PostLikeId(
    var post: Long,
    var user: Long,
): Serializable