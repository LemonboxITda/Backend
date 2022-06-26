package lemonbox.supplement.repository

import lemonbox.supplement.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<Long, User> {
    fun findByLoginId(loginId: Long): User?

    fun findByNickname(nickname: String): User?
}