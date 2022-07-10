package lemonbox.supplement.data

import lemonbox.supplement.entity.Comment
import java.time.Instant

data class CommentRequestDto(
    var content: String,
)

data class CommentResponseDto(
    var id: Long,
    var userInfo: UserInfo,
    var content: String,
    var createdAt: Instant
) {
    constructor(comment: Comment): this(comment.id, UserInfo(comment.user), comment.content, comment.createdAt)
}