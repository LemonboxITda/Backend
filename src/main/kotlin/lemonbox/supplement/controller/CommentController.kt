package lemonbox.supplement.controller

import lemonbox.supplement.data.CommentRequestDto
import lemonbox.supplement.service.CommentService
import lemonbox.supplement.utils.exception.ResponseMessage
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

@RestController
@RequestMapping("/comment")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @RequestParam postId: Long,
        @RequestBody requestDto: CommentRequestDto,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(commentService.createComment(postId, loginId, requestDto))
    }

    @GetMapping("/{postId}")
    fun getCommentList(@PathVariable postId: Long): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(commentService.readCommentList(postId))
    }

    @PutMapping
    fun editComment(@RequestParam commentId: Long, @RequestBody requestDto: CommentRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(commentService.editComment(commentId, requestDto))
    }

    @DeleteMapping
    fun deleteComment(@RequestParam commentId: Long): ResponseEntity<Any> {
        commentService.deleteComment(commentId)

        return ResponseEntity
            .ok()
            .body(ResponseMessage(HttpStatus.OK.value(), "성공"))
    }
}