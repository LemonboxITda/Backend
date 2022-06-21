package lemonbox.supplement.entity

import javax.persistence.*

@Entity
class Comment(

    @Column
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,

): BaseEntity()