package lemonbox.supplement.service

import lemonbox.supplement.data.PillDto
import lemonbox.supplement.entity.Pill
import lemonbox.supplement.repository.PillRepository
import lemonbox.supplement.repository.SupplementRepository
import lemonbox.supplement.repository.UserRepository
import lemonbox.supplement.utils.exception.CustomException
import lemonbox.supplement.utils.exception.ResponseCode
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PillService (
    private val pillRepository: PillRepository,
    private val supplementRepository: SupplementRepository,
    private val userRepository: UserRepository,
) {
    fun changeStatusByDate(requestDto: PillDto) {
        val supplement = supplementRepository.findById(requestDto.supplementId).orElseThrow {
            throw CustomException(ResponseCode.SUPPLEMENT_NOT_FOUND)
        }

        var pill: Pill
        if (pillRepository.existsBySupplementAndDate(supplement, requestDto.date)) {
            pill = pillRepository.findBySupplementAndDate(supplement, requestDto.date)!!
            pill.status = requestDto.status
        }
        else {
            pill = Pill(supplement, requestDto.status, requestDto.date)
        }
        pillRepository.save(pill)
    }

    fun readAllByUser(loginId: String, startedAt: LocalDate, endedAt: LocalDate): MutableList<PillDto> {
        val user = userRepository.findByLoginId(loginId)
        

    }

}