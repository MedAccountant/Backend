package com.ClinicBackend.demo.Security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Autowired
    lateinit private var userService:UserDetailsService

    @Autowired
    lateinit private var jwtFilter: JwtFilter

    @Bean
    fun passwordEncoder()=BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authenticationConfiguration:AuthenticationConfiguration)=
        authenticationConfiguration.authenticationManager

    @Bean
    fun daoAuthenticationProvider()=DaoAuthenticationProvider(passwordEncoder()).also{it.setUserDetailsService(userService)}

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{it.disable()}
            .cors { it.disable() }
            .authorizeHttpRequests{
                it//.requestMatchers("/{companyName}/admin/**").hasAuthority("Admin")

                    //.requestMatchers("/{companyName}/admin").hasAuthority()
                .anyRequest().permitAll()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling{it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))}
            .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter::class.java)
            .logout { logout: LogoutConfigurer<HttpSecurity?> -> logout.permitAll() }
        return http.build()
    }
}