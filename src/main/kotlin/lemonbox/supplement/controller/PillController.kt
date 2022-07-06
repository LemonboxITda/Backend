package lemonbox.supplement.controller

import io.swagger.v3.oas.annotations.tags.Tag
import lemonbox.supplement.service.PillService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest

@Tag(name = "pill", description = "알약 복용 여부 체크 API")
@RestController
@RequestMapping("/pill")
class PillController(
    private val pillService: PillService,
) {

//    @PostMapping()
//    fun checkOn(@RequestParam date: LocalDate): ResponseEntity<Any> {
//        return ResponseEntity
//            .ok()
//            .body(pillService.checkOnByDate(date))
//    }
//
//    @PutMapping()
//    fun checkOff(): ResponseEntity<Any> {
//
//    }
//
//    @GetMapping()
//    fun readAllByUserLoginId(request: HttpServletRequest): ResponseEntity<Any> {
//
//    }

}