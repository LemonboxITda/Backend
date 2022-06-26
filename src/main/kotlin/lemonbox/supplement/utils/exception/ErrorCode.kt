package lemonbox.supplement.utils.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String,
) {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "계정이 존재하지 않습니다."),
}