package lemonbox.supplement.controller

import lemonbox.supplement.data.PostRequestDto
import lemonbox.supplement.service.PostService
import lemonbox.supplement.utils.exception.ResponseMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService,
) {

    @PostMapping
    fun createPost(@RequestBody requestDto: PostRequestDto, request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(postService.createPost(requestDto, loginId))
    }

    // TODO: Paging
    @GetMapping
    fun readAll(): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(postService.readAll())
    }

    @GetMapping("/detail/{id}")
    fun readById(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(postService.readById(id))
    }

    @PutMapping
    fun editPost(@RequestParam id: Long, @RequestBody requestDto: PostRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(postService.updatePost(id, requestDto))
    }

    @DeleteMapping
    fun deletePost(@RequestParam id: Long): ResponseEntity<Any> {
        postService.deletePost(id)

        return ResponseEntity
            .ok()
            .body(ResponseMessage(HttpStatus.OK.value(), "성공"))
    }
}