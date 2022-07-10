package lemonbox.supplement.entity

import lemonbox.supplement.data.CommentRequestDto
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

): BaseEntity() {
    constructor(requestDto: CommentRequestDto, user: User, post: Post): this(
        content = requestDto.content,
        user = user,
        post = post,
    )
}