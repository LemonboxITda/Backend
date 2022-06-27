package lemonbox.supplement.service

import lemonbox.supplement.config.jwt.JwtTokenProvider
import lemonbox.supplement.data.RoleType
import lemonbox.supplement.data.SigninRequestDto
import lemonbox.supplement.data.SignupRequestDto
import lemonbox.supplement.data.SignupResponseDto
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
    fun signup(requestDto: SignupRequestDto): SignupResponseDto {
        if (validateNickname(requestDto.nickname)) throw CustomException(ErrorCode.USER_ID_DUPLICATED)
        if (validateLoginId(requestDto.loginId)) throw CustomException(ErrorCode.USER_NICKNAME_DUPLICATED)

        requestDto.password = passwordEncoder.encode(requestDto.password)
        return SignupResponseDto(userRepository.save(User(requestDto)))
    }

    fun signin(requestDto: SigninRequestDto) {
        
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