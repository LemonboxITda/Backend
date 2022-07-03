package lemonbox.supplement.utils.exception

class CustomException(
    val responseCode: ResponseCode
): RuntimeException()