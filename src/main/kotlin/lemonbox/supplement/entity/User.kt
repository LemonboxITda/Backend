package lemonbox.supplement.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lemonbox.supplement.data.RoleType
import java.time.LocalDate
import javax.persistence.*

@Entity
class User (

    @Column(unique = true)
    var login_id: Int,

    @JsonIgnore
    @Column
    var password: String,

    @Column
    var email: String,

    @Column
    var nickname: String,

    @Column
    var birth: LocalDate,

    @Column
    var role: RoleType,

    @Column
    var profile_image: String,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var supplementList: MutableList<Supplement> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var postList: MutableList<Post> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var commentList: MutableList<Comment> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var postLikeList: MutableList<PostLike> = mutableListOf()
): BaseEntity()