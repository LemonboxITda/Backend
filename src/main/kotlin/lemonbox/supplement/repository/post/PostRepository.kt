package lemonbox.supplement.repository.post

import lemonbox.supplement.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long>, PostDslRepository {
}