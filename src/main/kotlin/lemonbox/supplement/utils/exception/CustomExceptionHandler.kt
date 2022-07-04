package lemonbox.supplement.utils.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        return ErrorResponse.toResponseEntity(e.responseCode)
    }
}