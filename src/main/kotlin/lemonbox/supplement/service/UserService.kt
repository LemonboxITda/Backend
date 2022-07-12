package lemonbox.supplement.service

import lemonbox.supplement.data.UserInfo
import lemonbox.supplement.data.UserRequestDto
import lemonbox.supplement.entity.BaseEntity
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import net.bytebuddy.dynamic.loading.InjectionClassLoader.Strategy
import org.springframework.stereotype.Service
import javax.persistence.Column

@Service
class UserService (
    private val userRepository: UserRepository,
) {

    fun readByLoginId(loginId: String): UserInfo {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)

        return UserInfo(user)
    }

    fun updateUserInfo(loginId: String, requestDto: UserRequestDto): UserInfo {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
        user.nickname = requestDto.nickname
        userRepository.save(user)

        return UserInfo(user)
    }
}