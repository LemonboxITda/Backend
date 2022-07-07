package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.CheckDto
import lemonbox.supplement.data.PillRequestDto
import lemonbox.supplement.data.PillResponseDto
import lemonbox.supplement.service.PillService
import lemonbox.supplement.utils.exception.ErrorResponse
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
    @Operation(summary = "해당 날짜의 영양제 복용 여부 체크")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "체크 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ResponseMessage::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 영양제를 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PostMapping
    fun changeStatusByDate(@RequestBody requestDto: PillRequestDto, request: HttpServletRequest): ResponseEntity<Any> {
        pillService.changeStatusByDate(requestDto)

        return ResponseEntity
            .ok()
            .body(ResponseMessage(HttpStatus.OK.value(), "성공"))
    }

    @Operation(summary = "캘린더에서 각 날짜의 복용 여부 조회")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "조회 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = CheckDto::class))))])
    ])
    @GetMapping
    fun readAllStatusByDateBetween(
        @Parameter(description = "조회 기간의 시작 날짜") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam startedAt: LocalDate,
        @Parameter(description = "조회 기간의 끝 날짜") @DateTimeFormat(pattern = "yyyy-MM-dd")  @RequestParam endedAt: LocalDate,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(pillService.readAllStatusByDateBetween(loginId, startedAt, endedAt.plusDays(1)))
    }

    @Operation(summary = "해당 날짜의 영양제 복용 정보 조회")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "조회 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = PillResponseDto::class))))])
    ])
    @GetMapping("/date")
    fun readAllByDate(
        @Parameter(description = "조회할 날짜") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam date: LocalDate,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name

        return ResponseEntity
            .ok()
            .body(pillService.readAllByUserAndDate(loginId, date))
    }
}