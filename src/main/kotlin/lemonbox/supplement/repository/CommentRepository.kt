package lemonbox.supplement.repository

import lemonbox.supplement.entity.Comment
import lemonbox.supplement.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByPost(post: Post, pageable: Pageable): Page<Comment>
}