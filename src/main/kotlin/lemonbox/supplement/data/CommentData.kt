package lemonbox.supplement.data

import com.fasterxml.jackson.annotation.JsonFormat
import lemonbox.supplement.entity.Comment
import java.time.Instant

data class CommentRequestDto(
    var content: String,
)

data class CommentResponseDto(
    var id: Long,
    var writer: SimpleInfo,
    var content: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var createdAt: Instant
) {
    constructor(comment: Comment): this(comment.id, SimpleInfo(comment.user), comment.content, comment.createdAt)
}

data class CommentPage(
    var totalCount: Long,
    var pageCount: Int,
    var data: MutableList<CommentResponseDto>
)