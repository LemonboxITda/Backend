package lemonbox.supplement.data

import io.swagger.v3.oas.annotations.media.Schema
import lemonbox.supplement.entity.User
import java.time.LocalDate

data class SignUpRequestDto (
    @Schema(description = "로그인 ID")
    val loginId: String,
    @Schema(description = "패스워드")
    var password: String,
    @Schema(description = "닉네임")
    val nickname: String,
)

data class SignInRequestDto (
    @Schema(description = "로그인 ID")
    val loginId: String,
    @Schema(description = "패스워드")
    var password: String,
)

data class SignInResponseDto (
    @Schema(description = "JWT 토큰(Request의 Authorization 헤더에 입력해 전송")
    val accessToken: String,
    @Schema(description = "Refresh 토큰(미구현)")
    val refreshToken: String,
    @Schema(description = "회원 정보")
    val userInfo: UserInfo,
)

data class UserInfo (
    @Schema(description = "회원 인식용 ID")
    val id: Long,
    @Schema(description = "회원 로그인 ID")
    val loginId: String,
    @Schema(description = "회원 닉네임")
    val nickname: String,
    @Schema(description = "프로필 이미지 URL")
    val profileImage: String?,
    @Schema(description = "회원 이메일")
    val email: String?,
    @Schema(description = "회원 생일")
    val birth: LocalDate?,
) {
    constructor(user: User): this(user.id, user.loginId, user.nickname, user.profileImage, user.email, user.birth)
}

data class SimpleInfo (
    @Schema(description = "회원 인식용 ID")
    val id: Long,
    @Schema(description = "회원 로그인 ID")
    val loginId: String,
    @Schema(description = "회원 닉네임")
    val nickname: String,
    @Schema(description = "프로필 이미지 URL")
    val profileImage: String?,
) {
    constructor(user: User): this(user.id, user.loginId, user.nickname, user.profileImage)
}

data class UserRequestDto (
    var nickname: String,
)

data class UserPage(
    var totalCount: Long,
    var pageCount: Int,
    var data: MutableList<UserInfo>
)