package lemonbox.supplement.data

import lemonbox.supplement.entity.Comment
import java.time.Instant

data class CommentRequestDto(
    var content: String,
)

data class CommentResponseDto(
    var id: Long,
    var userInfo: SimpleInfo,
    var content: String,
    var createdAt: Instant
) {
    constructor(comment: Comment): this(comment.id, SimpleInfo(comment.user), comment.content, comment.createdAt)
}

data class CommentPage(
    var totalCount: Long,
    var pageCount: Int,
    var data: MutableList<CommentResponseDto>
)