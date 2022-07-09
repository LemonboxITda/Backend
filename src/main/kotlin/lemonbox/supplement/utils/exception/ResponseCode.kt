package lemonbox.supplement.utils.exception

import org.springframework.http.HttpStatus

enum class ResponseCode(
    val httpStatus: HttpStatus,
    val message: String,
) {
    OK(HttpStatus.OK, "성공"),

    USER_ID_DUPLICATED(HttpStatus.CONFLICT, "중복된 ID가 존재합니다."),
    USER_ID_INCORRECT(HttpStatus.BAD_REQUEST, "아이디 형식이 올바르지 않습니다."),

    USER_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "중복된 닉네임이 존재합니다."),
    USER_NICKNAME_INCORRECT(HttpStatus.BAD_REQUEST, "닉네임 형식이 올바르지 않습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "계정이 존재하지 않습니다."),
    USER_LOGIN_FAIL(HttpStatus.NOT_ACCEPTABLE, "아이디 또는 비밀번호가 틀렸습니다."),

    TOKEN_INVALID_SIGNATURE(HttpStatus.NOT_FOUND, "Invalid JWT signature"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Invalid JWT token"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Expired JWT token"),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "Unsupported JWT token"),
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "JWT claims string is empty"),

    SUPPLEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 영양제가 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 글이 존재하지 않습니다."),
}

data class ResponseMessage(
    val status: Int,
    val message: Any
)