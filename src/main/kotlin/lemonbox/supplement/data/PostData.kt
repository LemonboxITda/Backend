package lemonbox.supplement.data

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
    var writer: SimpleInfo,
    var createdAt: Instant,
) {
    constructor(post: Post): this(post.id, post.title, post.content, SimpleInfo(post.user), post.createdAt)
}

data class PostPage(
    var totalCount: Long,
    var pageCount: Int,
    var data: MutableList<PostResponseDto>
)