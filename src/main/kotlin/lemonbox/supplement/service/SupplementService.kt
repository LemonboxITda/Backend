package lemonbox.supplement.service

import lemonbox.supplement.data.SupplementRequestDto
import lemonbox.supplement.data.SupplementResponseDto
import lemonbox.supplement.data.UpdateRequestDto
import lemonbox.supplement.entity.Supplement
import lemonbox.supplement.repository.SupplementRepository
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.stereotype.Service

@Service
class SupplementService (
    private val supplementRepository: SupplementRepository,
    private val userRepository: UserRepository,
) {
    fun createSupplement(loginId: String, requestDto: SupplementRequestDto): SupplementResponseDto {
        val user = userRepository.findByLoginId(loginId)
            ?: throw CustomException(ResponseCode.USER_NOT_FOUND)

        val supplement = Supplement(requestDto, user)
        supplementRepository.save(supplement)
        return SupplementResponseDto(supplement)
    }

    fun updateCount(requestDto: UpdateRequestDto): SupplementResponseDto {
        val supplement = supplementRepository.findById(requestDto.id).orElseThrow {
            throw CustomException(ResponseCode.SUPPLEMENT_NOT_FOUND)
        }

        supplement.updateCount(requestDto.count)
        supplementRepository.save(supplement)
        return SupplementResponseDto(supplement)
    }

    fun deleteSupplement(id: Long) {
        val supplement = supplementRepository.findById(id).orElseThrow {
            throw CustomException(ResponseCode.SUPPLEMENT_NOT_FOUND)
        }
        supplementRepository.delete(supplement)
    }

    fun readAllByUserLoginId(loginId: String): List<SupplementResponseDto> {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)

        var responseDtoList = mutableListOf<SupplementResponseDto>()
        supplementRepository.findByUser(user).forEach {
            val responseDto = SupplementResponseDto(it)
            responseDtoList.add(responseDto)
        }
        return responseDtoList
    }
}