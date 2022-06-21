package lemonbox.supplement.entity

import javax.persistence.*

@Entity
class Post (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column
    var content: String,

    @Column
    var views: Int,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post")
    var commentList: MutableList<Comment> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "post")
    var postLikeList: MutableList<PostLike> = mutableListOf()

    ): BaseEntity()