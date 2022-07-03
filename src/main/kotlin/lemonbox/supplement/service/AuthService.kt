package lemonbox.supplement.service

import lemonbox.supplement.config.jwt.JwtTokenProvider
import lemonbox.supplement.data.*
import lemonbox.supplement.entity.User
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        if (validateNickname(requestDto.nickname) != ResponseCode.OK) throw CustomException(ResponseCode.USER_ID_DUPLICATED)
        if (validateLoginId(requestDto.loginId) != ResponseCode.OK) throw CustomException(ResponseCode.USER_NICKNAME_DUPLICATED)

        requestDto.password = passwordEncoder.encode(requestDto.password)
        return UserInfo(userRepository.save(User(requestDto)))
    }

    @Transactional
    fun signIn(requestDto: SignInRequestDto): SignInResponseDto {
        val user : User = userRepository.findByLoginId(requestDto.loginId)
            ?: throw CustomException(ResponseCode.USER_LOGIN_FAIL)

        if (!passwordEncoder.matches(requestDto.password, user.password))
            throw CustomException(ResponseCode.USER_LOGIN_FAIL)

        //TODO: refreshToken 넣기
        return SignInResponseDto(
            refreshToken = "",
            accessToken = createToken(user.loginId),
            userInfo = UserInfo(user)
        )
    }

    @Transactional(readOnly = true)
    fun validateNickname(input: String): ResponseCode {
        val nickname = input.replace(" ", "")
        val exp = Regex("^[가-힣ㄱ-ㅎ ㅏ-ㅣ a-zA-Z0-9 -]{2,12}\$")
        if (!exp.matches(nickname)) return ResponseCode.USER_NICKNAME_INCORRECT

        if (userRepository.existsByNickname(nickname)) return ResponseCode.USER_NICKNAME_DUPLICATED
        return ResponseCode.OK
    }

    @Transactional(readOnly = true)
    fun validateLoginId(input: String): ResponseCode {
        val loginId = input.replace(" ", "")
        val exp = Regex("^[a-zA-Z0-9 -]{2,12}\$")
        if (!exp.matches(loginId)) return ResponseCode.USER_ID_INCORRECT

        if (userRepository.existsByLoginId(loginId)) return ResponseCode.USER_ID_DUPLICATED
        return ResponseCode.OK
    }
}