package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.PostRequestDto
import lemonbox.supplement.data.PostResponseDto
import lemonbox.supplement.service.PostService
import lemonbox.supplement.utils.exception.ErrorResponse
import lemonbox.supplement.utils.exception.ResponseMessage
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Tag(name = "post", description = "커뮤니티 게시글 API")
@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService,
) {

    @Operation(summary = "게시글 생성 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ResponseMessage::class))))])
    ])
    @PostMapping
    fun createPost(@RequestBody requestDto: PostRequestDto, request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(postService.createPost(requestDto, loginId))
    }

    @Operation(summary = "게시글 리스트 조회 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = PostResponseDto::class))))]),
        ApiResponse(responseCode = "400", description = "size, page를 올바르게 입력해주세요.")
    ])
    @GetMapping
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
    @GetMapping("/detail/{postId}")
    fun readById(@Parameter(description = "게시글 ID") @PathVariable postId: Long): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(postService.readById(postId))
    }

    @Operation(summary = "게시글 수정 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = PostResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 게시글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PutMapping
    fun editPost(@Parameter(description = "게시글 ID") @RequestParam postId: Long, @RequestBody requestDto: PostRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(postService.updatePost(postId, requestDto))
    }

    @Operation(summary = "게시글 삭제 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ResponseMessage::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 게시글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @DeleteMapping
    fun deletePost(@Parameter(description = "게시글 ID") @RequestParam postId: Long): ResponseEntity<Any> {
        postService.deletePost(postId)

        return ResponseEntity
            .ok()
            .body(ResponseMessage(HttpStatus.OK.value(), "성공"))
    }
}