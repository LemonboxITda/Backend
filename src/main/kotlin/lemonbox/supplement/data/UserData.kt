package lemonbox.supplement.data

import lemonbox.supplement.entity.User
import java.time.LocalDate

data class SignupRequestDto (
    var loginId: String,
    var password: String,
    var nickname: String,
)

data class SignupResponseDto (
    var loginId: String,
    var nickname: String,
    var profileImage: String?,
    var email: String?,
    var birth: LocalDate?,
) {
    constructor(user: User): this(user.loginId, user.nickname, user.profileImage, user.email, user.birth)
}

data class SigninRequestDto (
    var loginId: String,
    var password: String,
)