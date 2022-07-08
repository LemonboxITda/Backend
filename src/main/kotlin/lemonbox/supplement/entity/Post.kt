package lemonbox.supplement.entity

import lemonbox.supplement.data.PostRequestDto
import javax.persistence.*

@Entity
class Post (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column
    var title: String,

    @Column
    var content: String,

    @Column
    var views: Int = 0,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post")
    var commentList: MutableList<Comment> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post")
    var postLikeList: MutableList<PostLike> = mutableListOf()

): BaseEntity() {
    constructor(requestDto: PostRequestDto, user: User): this(
        user = user,
        title = requestDto.title,
        content = requestDto.content
    )
}