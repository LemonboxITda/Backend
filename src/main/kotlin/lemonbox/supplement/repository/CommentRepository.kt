package lemonbox.supplement.repository

import lemonbox.supplement.entity.Comment
import lemonbox.supplement.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {

    fun findByPost(post: Post): List<Comment>
}