package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.HeartResponseDto
import lemonbox.supplement.service.HeartService
import lemonbox.supplement.utils.exception.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@Tag(name = "heart", description = "좋아요 API")
@RestController
@RequestMapping("/heart")
class HeartController(
    private val heartService: HeartService
) {
    @Operation(summary = "좋아요 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = HeartResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 게시글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PostMapping("/{postId}")
    fun likePost(
        @Parameter(description = "게시글 ID") @PathVariable postId: Long,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(heartService.likePost(loginId, postId))
    }

    @Operation(summary = "좋아요 취소 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = HeartResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 게시글을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @DeleteMapping("/{postId}")
    fun unlikePost(
        @Parameter(description = "게시글 ID") @PathVariable postId: Long,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(heartService.unlikePost(loginId, postId))
    }
}