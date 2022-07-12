package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.CommentRequestDto
import lemonbox.supplement.data.CommentResponseDto
import lemonbox.supplement.data.PostResponseDto
import lemonbox.supplement.service.CommentService
import lemonbox.supplement.utils.exception.ErrorResponse
import lemonbox.supplement.utils.exception.ResponseMessage
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@Tag(name = "comment", description = "댓글 API")
@RestController
@RequestMapping("/comment")
class CommentController(
    private val commentService: CommentService
) {

    @Operation(summary = "댓글 생성 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = CommentResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 게시글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PostMapping
    fun createComment(
        @Parameter(description = "게시글 ID") @RequestParam postId: Long,
        @RequestBody requestDto: CommentRequestDto,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(commentService.createComment(postId, loginId, requestDto))
    }

    @Operation(summary = "게시글에 달린 댓글 리스트 조회 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = CommentResponseDto::class))))]),
        ApiResponse(responseCode = "400", description = "size, page를 올바르게 입력해주세요."),
        ApiResponse(responseCode = "404", description = "해당 ID의 게시글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @GetMapping("/{postId}")
    fun getCommentList(
        @Parameter(description = "게시글 ID") @PathVariable postId: Long,
        @Parameter(description = "페이징 크기") @RequestParam size: Int,
        @Parameter(description = "페이지") @RequestParam page: Int
    ): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(commentService.readCommentList(postId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))))
    }

    @Operation(summary = "댓글 수정 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = CommentResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 댓글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PutMapping
    fun editComment(
        @Parameter(description = "댓글 ID") @RequestParam commentId: Long,
        @RequestBody requestDto: CommentRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(commentService.editComment(commentId, requestDto))
    }

    @Operation(summary = "댓글 삭제 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = CommentResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 댓글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @DeleteMapping
    fun deleteComment(@Parameter(description = "댓글 ID") @RequestParam commentId: Long): ResponseEntity<Any> {
        commentService.deleteComment(commentId)

        return ResponseEntity
            .ok()
            .body(ResponseMessage(HttpStatus.OK.value(), "성공"))
    }
}