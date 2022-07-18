package lemonbox.supplement.service

import lemonbox.supplement.data.*
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val userRepository: UserRepository,
) {

    @Transactional(readOnly = true)
    fun readAll(pageable: Pageable): UserPage {
        val userList = mutableListOf<UserInfo>()
        val pages = userRepository.findAll(pageable)

        pages.forEach {
            userList.add(UserInfo(it))
        }
        return UserPage(pages.totalElements, pages.totalPages, userList)
    }

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