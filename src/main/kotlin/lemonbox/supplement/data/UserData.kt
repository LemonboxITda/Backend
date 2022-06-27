package lemonbox.supplement.data

import lemonbox.supplement.entity.User
import java.time.LocalDate

data class SignUpRequestDto (
    var loginId: String,
    var password: String,
    var nickname: String,
)

data class SignInRequestDto (
    var loginId: String,
    var password: String,
)

data class SignInResponseDto (
    var accessToken: String,
    var refreshToken: String,
    var userInfo: UserInfo,
)

data class UserInfo (
    var id: Long,
    var loginId: String,
    var nickname: String,
    var profileImage: String?,
    var email: String?,
    var birth: LocalDate?,
) {
    constructor(user: User): this(user.id, user.loginId, user.nickname, user.profileImage, user.email, user.birth)
}