package lemonbox.supplement.controller

import lemonbox.supplement.data.SignInRequestDto
import lemonbox.supplement.data.SignUpRequestDto
import lemonbox.supplement.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/signup")
    fun signup(@RequestBody requestDto: SignUpRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(authService.signUp(requestDto))
    }

    @PostMapping("/signin")
    fun signin(@RequestBody requestDto: SignInRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(authService.signIn(requestDto))
    }
}