package lemonbox.supplement.data

import lemonbox.supplement.entity.Post

data class PostRequestDto(
    var title: String,
    var content: String
)

data class PostResponseDto(
    var id: Long?,
    var title: String,
    var content: String,
    var writer: UserInfo
) {
    constructor(post: Post): this(post.id, post.title, post.content, UserInfo(post.user))
}