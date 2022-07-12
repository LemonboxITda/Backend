package lemonbox.supplement.repository

import lemonbox.supplement.entity.Heart
import lemonbox.supplement.entity.Post
import lemonbox.supplement.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HeartRepository: JpaRepository<Heart, Long> {
    fun findByUserAndPost(user: User, post: Post): Heart?

    fun existsByUserAndPost(user: User, post: Post): Boolean
}