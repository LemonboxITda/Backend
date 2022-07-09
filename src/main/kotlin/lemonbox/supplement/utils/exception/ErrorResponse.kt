package lemonbox.supplement.utils.exception

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

class ErrorResponse(
    val status: Int,
    val error: String,
    val code: String,
    val message: String,
) {
    companion object {
        fun toResponseEntity(responseCode: ResponseCode): ResponseEntity<ErrorResponse> {
            return ResponseEntity
                .status(responseCode.httpStatus.value())
                .body(
                    ErrorResponse(
                        status = responseCode.httpStatus.value(),
                        error = responseCode.httpStatus.name,
                        code = responseCode.name,
                        message = responseCode.message
                    )
                )
        }
    }
}