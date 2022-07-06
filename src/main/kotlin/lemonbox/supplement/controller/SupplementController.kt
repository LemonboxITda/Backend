package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.SupplementRequestDto
import lemonbox.supplement.service.SupplementService
import lemonbox.supplement.utils.exception.ResponseMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Tag(name = "supplement", description = "영양제 API")
@RestController
@RequestMapping("/supplement")
class SupplementController(
    private val supplementService: SupplementService,
) {

    @PostMapping
    fun createSupplement(@RequestBody requestDto: SupplementRequestDto, request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name
        return ResponseEntity
            .ok()
            .body(supplementService.createSupplement(loginId, requestDto))
    }

    @PutMapping
    fun updateCount(
        @RequestParam count: Int,
        @RequestParam supplementId: Long
    ): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(supplementService.updateCount(supplementId, count))
    }

    @GetMapping
    fun readAllByUser(request: HttpServletRequest): ResponseEntity<Any> {
        // TODO: SecurityConfig에 접근 가능한 URL 설정
        val loginId = request.userPrincipal!!.name

        return ResponseEntity
            .ok()
            .body(supplementService.readAllByUserLoginId(loginId))
    }

    @DeleteMapping
    fun deleteSupplement(@RequestParam supplementId: Long): ResponseEntity<Any> {
        supplementService.deleteSupplement(supplementId)

        return ResponseEntity
            .ok()
            .body(
                ResponseMessage(
                    status = HttpStatus.OK.value(),
                    message = "성공"
                )
            )
    }
}