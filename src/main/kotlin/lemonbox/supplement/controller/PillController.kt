package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.PillRequestDto
import lemonbox.supplement.service.PillService
import lemonbox.supplement.utils.exception.ResponseMessage
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest

@Tag(name = "pill", description = "알약 복용 여부 체크 API")
@RestController
@RequestMapping("/pill")
class PillController(
    private val pillService: PillService,
) {
    @PostMapping()
    fun changeStatusByDate(@RequestBody requestDto: PillRequestDto, request: HttpServletRequest): ResponseEntity<Any> {
        pillService.changeStatusByDate(requestDto)

        return ResponseEntity
            .ok()
            .body(ResponseMessage(HttpStatus.OK.value(), "성공"))
    }

    @GetMapping()
    fun readAllStatusByDateBetween(
        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam startedAt: LocalDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam endedAt: LocalDate,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(pillService.readAllStatusByDateBetween(loginId, startedAt, endedAt))
    }

    @GetMapping("/date")
    fun readAllByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam date: LocalDate, request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(pillService.readAllByUserAndDate(loginId, date))
    }
}