package lemonbox.supplement.data

import io.swagger.v3.oas.annotations.media.Schema
import lemonbox.supplement.entity.User
import java.time.LocalDate

data class SignUpRequestDto (
    @Schema(description = "로그인 ID")
    var loginId: String,
    @Schema(description = "패스워드")
    var password: String,
    @Schema(description = "닉네임")
    var nickname: String,
)

data class SignInRequestDto (
    @Schema(description = "로그인 ID")
    var loginId: String,
    @Schema(description = "패스워드")
    var password: String,
)

data class SignInResponseDto (
    @Schema(description = "JWT 토큰(Request의 Authorization 헤더에 입력해 전송")
    var accessToken: String,
    @Schema(description = "Refresh 토큰(미구현)")
    var refreshToken: String,
    @Schema(description = "회원 정보")
    var userInfo: UserInfo,
)

data class UserInfo (
    @Schema(description = "회원 인식용 ID")
    var id: Long,
    @Schema(description = "회원 로그인 ID")
    var loginId: String,
    @Schema(description = "회원 닉네임")
    var nickname: String,
    @Schema(description = "프로필 이미지 URL")
    var profileImage: String?,
    @Schema(description = "회원 이메일")
    var email: String?,
    @Schema(description = "회원 생일")
    var birth: LocalDate?,
) {
    constructor(user: User): this(user.id, user.loginId, user.nickname, user.profileImage, user.email, user.birth)
}