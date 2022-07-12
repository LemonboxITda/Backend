package lemonbox.supplement.service

import lemonbox.supplement.data.PostPage
import lemonbox.supplement.data.PostRequestDto
import lemonbox.supplement.data.PostResponseDto
import lemonbox.supplement.entity.Post
import lemonbox.supplement.repository.PostRepository
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService (
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createPost(requestDto: PostRequestDto, loginId: String): PostResponseDto {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
        val post = Post(requestDto, user)

        postRepository.save(post)
        return PostResponseDto(post)
    }

    @Transactional(readOnly = true)
    fun readAll(pageable: Pageable): PostPage {
        val postList = mutableListOf<PostResponseDto>()
        val pages = postRepository.findAll(pageable)

        pages.forEach {
            postList.add(PostResponseDto(it))
        }

        return PostPage(pages.totalElements, pages.totalPages, postList)
    }

    @Transactional
    fun readById(id: Long): PostResponseDto {
        val post = postRepository.findById(id).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }
        post.views++
        postRepository.save(post)
        return PostResponseDto(post)
    }

    @Transactional
    fun updatePost(id: Long, requestDto: PostRequestDto): PostResponseDto {
        val post = postRepository.findById(id).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }

        post.title = requestDto.title
        post.content = requestDto.content
        postRepository.save(post)

        return PostResponseDto(post)
    }

    @Transactional
    fun deletePost(id: Long) {
        val post = postRepository.findById(id).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }
        postRepository.delete(post)
    }
}