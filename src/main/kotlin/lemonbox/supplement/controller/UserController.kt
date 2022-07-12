package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.UserInfo
import lemonbox.supplement.data.UserRequestDto
import lemonbox.supplement.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Tag(name = "user", description = "회원정보 API")
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "회원 정보 조회")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "조회 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = UserInfo::class))))]),
    ])
    @GetMapping
    fun readUserInfo(request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(userService.readByLoginId(loginId))
    }

    @Operation(summary = "회원 정보 수정")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "조회 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = UserInfo::class))))]),
    ])
    @PutMapping
    fun updateUserInfo(@RequestBody requestDto: UserRequestDto, request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(userService.updateUserInfo(loginId, requestDto))
    }
}