package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.SignInRequestDto
import lemonbox.supplement.data.SignInResponseDto
import lemonbox.supplement.data.SignUpRequestDto
import lemonbox.supplement.data.UserInfo
import lemonbox.supplement.service.AuthService
import lemonbox.supplement.utils.exception.ErrorResponse
import lemonbox.supplement.utils.exception.ResponseCode
import lemonbox.supplement.utils.exception.ResponseMessage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "auth", description = "로그인/회원가입 API")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {

    @Operation(summary = "회원가입")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "회원가입 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = UserInfo::class))))]),
        ApiResponse(responseCode = "409", description = "이미 같은 아이디를 사용하는 유저가 존재합니다.", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))]),
        ApiResponse(responseCode = "409", description = "이미 같은 이름을 사용하는 유저가 존재합니다.", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PostMapping("/signup")
    fun signup(@RequestBody requestDto: SignUpRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(authService.signUp(requestDto))
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "로그인 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = SignInResponseDto::class))))]),
        ApiResponse(responseCode = "400", description = "ID 또는 비밀번호가 틀렸습니다.", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))]),
            ])
    @PostMapping("/signin")
    fun signin(@RequestBody requestDto: SignInRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(authService.signIn(requestDto))
    }

    @GetMapping("/check/nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<Any> {
        val responseCode = authService.validateNickname(nickname)

        if (responseCode == ResponseCode.OK)
            return ResponseEntity
                .ok()
                .body(
                    ResponseMessage(
                        status = responseCode.httpStatus.value(),
                        message = "사용 가능한 닉네임입니다."
                    )
                )

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

    @GetMapping("/check/id")
    fun checkLoginId(@RequestParam loginId: String): ResponseEntity<Any> {
        val responseCode = authService.validateLoginId(loginId)

        if (responseCode == ResponseCode.OK)
            return ResponseEntity
                .ok()
                .body(
                    ResponseMessage(
                        status = responseCode.httpStatus.value(),
                        message = "사용 가능한 아이디입니다."
                    )
                )

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