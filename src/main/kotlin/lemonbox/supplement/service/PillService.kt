package lemonbox.supplement.service

import lemonbox.supplement.data.CheckDto
import lemonbox.supplement.data.PillRequestDto
import lemonbox.supplement.data.PillResponseDto
import lemonbox.supplement.data.type.PillStatus
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
    fun changeStatusByDate(requestDto: PillRequestDto) {
        val supplement = supplementRepository.findById(requestDto.supplementId).orElseThrow {
            throw CustomException(ResponseCode.SUPPLEMENT_NOT_FOUND)
        }

        var pill = pillRepository.findBySupplementAndDate(supplement, requestDto.date)
        if (pill != null) {
            pill.status = requestDto.status
        }
        else {
            pill = Pill(supplement, requestDto.status, requestDto.date)
        }
        pillRepository.save(pill)
    }

    fun readAllStatusByDateBetween(loginId: String, startedAt: LocalDate, endedAt: LocalDate): MutableList<CheckDto> {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)
        var supplements = supplementRepository.findByUser(user)
        var response = mutableListOf<CheckDto>()

        // TODO: 리팩토링 필요
        var date = startedAt
        while (date.isBefore(endedAt)) {
            var pillList = pillRepository.findByUserAndDateAndStatus(user, date, PillStatus.IS_CHECKED)
            if (pillList.isNotEmpty() && pillList.size == supplements.size) {
                response.add(CheckDto(date, true))
            }
            else {
                response.add(CheckDto(date, false))
            }

            date = date.plusDays(1)
        }
        return response
    }

    fun readAllByUserAndDate(loginId: String, date: LocalDate): MutableList<PillResponseDto> {
        val user = userRepository.findByLoginId(loginId)?: throw CustomException(ResponseCode.USER_NOT_FOUND)

        val todayPillList = pillRepository.selectByUserAndDate(user, date)
        val supplements = supplementRepository.findByUser(user)
        val response = mutableListOf<PillResponseDto>()

        supplements.forEach {
            var pill: Pill? = null
            for (item in todayPillList) {
                if (item.supplement == it) {
                    pill = item
                    break
                }
            }

            if (pill != null)
                response.add(PillResponseDto(pill))
            else
                response.add(PillResponseDto(it.id, it.name, date, PillStatus.IS_NOT_CHECKED))
        }
        return response
    }
}