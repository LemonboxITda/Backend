package lemonbox.supplement.service

import lemonbox.supplement.data.PostRequestDto
import lemonbox.supplement.data.PostResponseDto
import lemonbox.supplement.entity.BaseEntity
import lemonbox.supplement.entity.Post
import lemonbox.supplement.repository.PostRepository
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.stereotype.Service

@Service
class PostService (
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {
    fun createPost(requestDto: PostRequestDto, loginId: String): PostResponseDto {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
        val post = Post(requestDto, user)

        postRepository.save(post)
        return PostResponseDto(post)
    }

    fun readAll(): MutableList<PostResponseDto> {
        val response = mutableListOf<PostResponseDto>()

        postRepository.findAll().forEach {
            response.add(PostResponseDto(it))
        }
        return response
    }

    fun readById(id: Long): PostResponseDto {
        val post = postRepository.findById(id).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }
        return PostResponseDto(post)
    }

    fun updatePost(id: Long, requestDto: PostRequestDto): PostResponseDto {
        val post = postRepository.findById(id).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }

        post.title = requestDto.title
        post.content = requestDto.content
        postRepository.save(post)

        return PostResponseDto(post)
    }

    fun deletePost(id: Long) {
        val post = postRepository.findById(id).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }
        postRepository.delete(post)
    }
}