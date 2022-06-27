package lemonbox.supplement.repository

import lemonbox.supplement.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByLoginId(loginId: String): User?

    fun findByNickname(nickname: String): User?

    fun existsByNickname(nickname: String): Boolean

    fun existsByLoginId(loginId: String): Boolean
}