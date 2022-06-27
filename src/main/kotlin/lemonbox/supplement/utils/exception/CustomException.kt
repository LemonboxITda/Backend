package lemonbox.supplement.utils.exception

class CustomException(
    val errorCode: ErrorCode
): RuntimeException()