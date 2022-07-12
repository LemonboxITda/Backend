package lemonbox.supplement.entity

import org.springframework.data.annotation.CreatedDate
import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
class Heart (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,
): BaseEntity()