package lemonbox.supplement.controller

import lemonbox.supplement.data.SigninRequestDto
import lemonbox.supplement.data.SignupRequestDto
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
    fun signup(@RequestBody requestDto: SignupRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(authService.signup(requestDto))
    }

    @PostMapping("/signin")
    fun signin(@RequestBody requestDto: SigninRequestDto): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(authService.signin(requestDto))
    }
}