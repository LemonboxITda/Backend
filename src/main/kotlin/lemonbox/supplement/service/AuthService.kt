package lemonbox.supplement.service

import lemonbox.supplement.config.jwt.JwtTokenProvider
import lemonbox.supplement.data.*
import lemonbox.supplement.entity.User
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    private fun createToken(userId: String): String {
        val authorities = ArrayList<String>()
        authorities.add("ROLE_USER")
        return jwtTokenProvider.getAccessToken(userId, authorities.toTypedArray())
    }

    @Transactional
    fun signUp(requestDto: SignUpRequestDto): UserInfo {
        if (validateNickname(requestDto.nickname)) throw CustomException(ErrorCode.USER_ID_DUPLICATED)
        if (validateLoginId(requestDto.loginId)) throw CustomException(ErrorCode.USER_NICKNAME_DUPLICATED)

        requestDto.password = passwordEncoder.encode(requestDto.password)
        return UserInfo(userRepository.save(User(requestDto)))
    }

    @Transactional
    fun signIn(requestDto: SignInRequestDto): SignInResponseDto {
        val user : User = userRepository.findByLoginId(requestDto.loginId)
            ?: throw CustomException(ErrorCode.USER_LOGIN_FAIL)

        if (!passwordEncoder.matches(requestDto.password, user.password))
            throw CustomException(ErrorCode.USER_LOGIN_FAIL)

        //TODO: refreshToken 넣기
        return SignInResponseDto(
            refreshToken = "",
            accessToken = createToken(user.loginId),
            userInfo = UserInfo(user)
        )
    }

    fun validateNickname(nickname: String): Boolean {
        //TODO: 영문 숫자로 이루어져 있는지?
        return userRepository.existsByLoginId(nickname)
    }

    fun validateLoginId(loginId: String): Boolean {
        //TODO: 영문 숫자로 이루어져 있는지?
        return userRepository.existsByLoginId(loginId)
    }
}