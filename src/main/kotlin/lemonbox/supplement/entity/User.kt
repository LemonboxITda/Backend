package lemonbox.supplement.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lemonbox.supplement.data.RoleType
import lemonbox.supplement.data.SignUpRequestDto
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import javax.persistence.*

@Entity
@NoArgsConstructor
class User (
    @Column(unique = true)
    var loginId: String,

    @JsonIgnore
    @Column
    private var password: String,

    @Column
    var nickname: String,

    @Column
    @Enumerated(EnumType.STRING)
    var role: RoleType,

    @Column
    var email: String?,

    @Column
    var birth: LocalDate?,

    @Column
    var profileImage: String?,
): BaseEntity(), UserDetails {

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var supplementList: MutableList<Supplement> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var postList: MutableList<Post> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var commentList: MutableList<Comment> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var postLikeList: MutableList<PostLike> = mutableListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    constructor(requestDto: SignUpRequestDto): this(
        loginId = requestDto.loginId,
        password = requestDto.password,
        nickname = requestDto.nickname,
        profileImage = null,
        email = null,
        birth = null,
        role = RoleType.ROLE_USER
    )
}