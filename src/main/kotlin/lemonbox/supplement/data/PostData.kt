package lemonbox.supplement.data

import com.fasterxml.jackson.annotation.JsonFormat
import lemonbox.supplement.entity.Post
import java.time.Instant

data class PostRequestDto(
    var title: String,
    var content: String
)

data class PostResponseDto(
    var id: Long?,
    var title: String,
    var content: String,
    var views: Int,
    var likeCount: Int,
    var writer: SimpleInfo,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var createdAt: Instant,
) {
    constructor(post: Post): this(
        post.id,
        post.title,
        post.content,
        post.views,
        post.heartList.size,
        SimpleInfo(post.user),
        post.createdAt
    )
}

data class PostPage(
    var totalCount: Long,
    var pageCount: Int,
    var data: MutableList<PostResponseDto>
)