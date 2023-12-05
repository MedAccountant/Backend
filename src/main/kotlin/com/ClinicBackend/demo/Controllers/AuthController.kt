package com.ClinicBackend.demo.Controllers

import com.ClinicBackend.demo.Security.AuthRequest
import com.ClinicBackend.demo.Security.AuthResponse
import com.ClinicBackend.demo.Security.JwtProvider
import com.ClinicBackend.demo.Security.UserDetailService.CustomUserDetailsService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {
    @Autowired
    lateinit private var userDetailsService: CustomUserDetailsService
    @Autowired
    lateinit private var jwtProvider: JwtProvider
    @Autowired
    lateinit private var authenticationManager: AuthenticationManager

    @PostMapping("/auth")
    fun createAuthToken(@RequestBody authRequest: AuthRequest): ResponseEntity<Any> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.login,
                    authRequest.password
                )
            )
        } catch (badCreadentials: BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build()
        }
        val userDetails=userDetailsService.loadUserByUsername(authRequest.login)
        val token=jwtProvider.generateToken(userDetails)
        println("token: $token")
        return ResponseEntity.ok(AuthResponse(token))
    }
}