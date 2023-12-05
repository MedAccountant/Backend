package com.ClinicBackend.demo.Security

import com.ClinicBackend.demo.Security.UserDetailService.CustomUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter:OncePerRequestFilter() {
    val AUTHORIZATION:String="Authorization"

    @Autowired
    lateinit private var jwtProvider: JwtProvider

    @Autowired
    lateinit private var customUserDetailsService: CustomUserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var userLogin:String?=null
        println("do filter...")
        val token=getTokenFromRequest(request)
        println("token: $token")
        if(token!=null)try{
            userLogin=jwtProvider.getLoginFromToken(token)
        }catch(e:ExpiredJwtException){
            println("Token is expired")
        }catch (e:SignatureException){
            println("Bad token")
        }
        if(userLogin!=null && SecurityContextHolder.getContext().authentication==null){
            val auth:UsernamePasswordAuthenticationToken= UsernamePasswordAuthenticationToken(userLogin,
                null,
                jwtProvider.getDepartmentsFromToken(token!!).map { SimpleGrantedAuthority(it.toString()) }.toMutableSet()
                    .also { it.add(SimpleGrantedAuthority(jwtProvider.getRoleFromToken(token!!))) }
                    )
            SecurityContextHolder.getContext().authentication=auth
        }
        filterChain.doFilter(request,response)
    }

    fun getTokenFromRequest(request: HttpServletRequest):String?{
        val bearer:String?=request.getHeader(AUTHORIZATION)
        if(bearer!=null && bearer.startsWith("Bearer "))
            return bearer.substring(7)
        return null
    }
}