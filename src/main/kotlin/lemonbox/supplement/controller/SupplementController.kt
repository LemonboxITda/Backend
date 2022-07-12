package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.data.SupplementRequestDto
import lemonbox.supplement.data.SupplementResponseDto
import lemonbox.supplement.data.UpdateRequestDto
import lemonbox.supplement.service.SupplementService
import lemonbox.supplement.utils.exception.ErrorResponse
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
    @Operation(summary = "영양제 생성")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "생성 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = SupplementResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 회원을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PostMapping
    fun createSupplement(@RequestBody requestDto: SupplementRequestDto, request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal.name
        return ResponseEntity
            .ok()
            .body(supplementService.createSupplement(loginId, requestDto))
    }

    @Operation(summary = "영양제 알약 개수 수정")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "수정 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = SupplementResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 영양제를 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @PutMapping
    fun updateCount(
        @RequestBody requestDto: UpdateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(supplementService.updateCount(requestDto))
    }

    @Operation(summary = "회원의 영양제 리스트 조회")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "조회 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = SupplementResponseDto::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 회원을 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @GetMapping
    fun readAllByUser(request: HttpServletRequest): ResponseEntity<Any> {
        val loginId = request.userPrincipal!!.name

        return ResponseEntity
            .ok()
            .body(supplementService.readAllByUserLoginId(loginId))
    }

    @Operation(summary = "영양제 삭제")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "삭제 성공", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ResponseMessage::class))))]),
        ApiResponse(responseCode = "404", description = "해당 ID의 영양제를 찾을 수 없습니다..", content = [
            Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = ErrorResponse::class))))])
    ])
    @DeleteMapping
    fun deleteSupplement(@Parameter(description = "영양제 ID") @RequestParam supplementId: Long): ResponseEntity<Any> {
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