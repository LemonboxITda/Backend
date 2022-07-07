package lemonbox.supplement.repository

import lemonbox.supplement.entity.PostLike
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostLikeRepository: JpaRepository<PostLike, Long> {
}