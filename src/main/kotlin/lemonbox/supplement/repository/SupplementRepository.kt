package lemonbox.supplement.repository

import lemonbox.supplement.entity.Supplement
import lemonbox.supplement.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupplementRepository: JpaRepository<Supplement, Long> {
    fun findByUser(user: User): List<Supplement>
}