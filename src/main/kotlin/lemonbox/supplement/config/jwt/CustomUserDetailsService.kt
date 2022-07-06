package lemonbox.supplement.config.jwt

import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByLoginId(username)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
    }
}