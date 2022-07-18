package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.PostResponseDto
import lemonbox.supplement.data.UserInfo
import lemonbox.supplement.service.PostService
import lemonbox.supplement.service.UserService
import lemonbox.supplement.utils.exception.ErrorResponse
import lemonbox.supplement.utils.exception.ResponseMessage
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "admin", description = "ADMIN API")
@RestController
@RequestMapping("/admin")
class AdminController(
    private val userService: UserService,
    private val postService: PostService,
) {
    @Operation(summary = "모든 회원 조회")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = UserInfo::class))))])
    ])
    @GetMapping("/users")
    fun readAllUsers(@Parameter(description = "페이징 크기") @RequestParam size: Int, @Parameter(description = "페이지") @RequestParam page: Int) : ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(userService.readAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))))
    }

    @Operation(summary = "유저 조회 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = UserInfo::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 유저를 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @GetMapping("/user/{loginId}")
    fun readUser(@PathVariable loginId: String): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(userService.readByLoginId(loginId))
    }

    @Operation(summary = "모든 게시글 조회 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = PostResponseDto::class))))])
    ])
    @GetMapping("/posts")
    fun readAll(@Parameter(description = "페이징 크기") @RequestParam size: Int, @Parameter(description = "페이지") @RequestParam page: Int): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(postService.readAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))))
    }

    @Operation(summary = "게시글 상세정보 조회 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = PostResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 게시글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @GetMapping("/post/{postId}")
    fun readPost(@PathVariable postId: Long): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(postService.readById(postId))
    }
}