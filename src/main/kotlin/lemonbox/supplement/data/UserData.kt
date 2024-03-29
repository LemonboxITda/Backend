package lemonbox.supplement.data

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import lemonbox.supplement.data.type.RoleType
import lemonbox.supplement.entity.User
import java.time.Instant
import java.time.LocalDate

data class SignUpRequestDto (
    @Schema(description = "로그인 ID")
    val loginId: String,
    @Schema(description = "패스워드")
    var password: String,
    @Schema(description = "닉네임")
    val nickname: String,
    @Schema(description = "닉네임")
    val role: RoleType,

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
    @Schema(description = "회원 권한")
    val role: RoleType
) {
    constructor(user: User): this(user.id, user.loginId, user.nickname, user.profileImage, user.email, user.birth, user.role)
}

data class UserAdminInfo (
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
    @Schema(description = "회원 권한")
    val role: RoleType,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var createdAt: Instant,
    var supplements: MutableList<SupplementResponseDto> = mutableListOf()
) {
    constructor(user: User): this(user.id, user.loginId, user.nickname, user.profileImage, user.email, user.birth, user.role, user.createdAt) {
        user.supplementList.forEach {
            supplements.add(SupplementResponseDto(it))
        }
    }
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
    var data: MutableList<UserAdminInfo>
)