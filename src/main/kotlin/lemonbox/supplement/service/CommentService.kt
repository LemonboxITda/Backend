package lemonbox.supplement.service

import lemonbox.supplement.data.CommentRequestDto
import lemonbox.supplement.data.CommentResponseDto
import lemonbox.supplement.entity.BaseEntity
import lemonbox.supplement.entity.Comment
import lemonbox.supplement.repository.CommentRepository
import lemonbox.supplement.repository.PostRepository
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createComment(postId: Long, userLoginId: String, requestDto: CommentRequestDto): CommentResponseDto {
        val user = userRepository.findByLoginId(userLoginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
        val post = postRepository.findById(postId).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }

        val comment = Comment(requestDto, user, post)
        commentRepository.save(comment)

        return CommentResponseDto(comment)
    }

    @Transactional(readOnly = true)
    fun readCommentList(postId: Long): MutableList<CommentResponseDto> {
        val response = mutableListOf<CommentResponseDto>()
        val post = postRepository.findById(postId).orElseThrow { throw CustomException(ResponseCode.POST_NOT_FOUND) }

        commentRepository.findByPost(post).forEach {
            response.add(CommentResponseDto(it))
        }
        return response
    }

    @Transactional
    fun editComment(commentId: Long, requestDto: CommentRequestDto): CommentResponseDto {
        val comment = commentRepository.findById(commentId).orElseThrow { throw CustomException(ResponseCode.COMMENT_NOT_FOUND) }
        comment.content = requestDto.content

        commentRepository.save(comment)
        return CommentResponseDto(comment)
    }

    @Transactional
    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findById(commentId).orElseThrow { throw CustomException(ResponseCode.COMMENT_NOT_FOUND) }
        commentRepository.delete(comment)
    }
}