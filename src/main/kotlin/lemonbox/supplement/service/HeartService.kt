package lemonbox.supplement.service

import lemonbox.supplement.data.HeartResponseDto
import lemonbox.supplement.entity.Heart
import lemonbox.supplement.repository.HeartRepository
import lemonbox.supplement.repository.PostRepository
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.stereotype.Service

@Service
class HeartService(
    private val heartRepository: HeartRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) {
    fun likePost(loginId: String, postId: Long): HeartResponseDto {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
        val post = postRepository.findById(postId).orElseThrow {
            throw CustomException(ResponseCode.POST_NOT_FOUND)
        }

        val isLiked = heartRepository.existsByUserAndPost(user, post)
        if (isLiked) throw CustomException(ResponseCode.ALREADY_LIKED)

        val heart = Heart(post, user)
        heartRepository.save(heart)

        return HeartResponseDto(heartCount = post.heartList.size)
    }

    fun unlikePost(loginId: String, postId: Long): HeartResponseDto {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
        val post = postRepository.findById(postId).orElseThrow {
            throw CustomException(ResponseCode.POST_NOT_FOUND)
        }

        val heart = heartRepository.findByUserAndPost(user, post)?: throw CustomException(ResponseCode.ALREADY_UNLIKED)
        heartRepository.delete(heart)

        return HeartResponseDto(heartCount = post.heartList.size)
    }
}