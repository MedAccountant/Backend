package com.ClinicBackend.demo.Security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.java.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
@Log
class JwtProvider {
    @Value("\${jwt.secret}")
    lateinit private var jwtSecret:String

    @Value("\${jwt.lifetime}")
    lateinit private var jwtLifetime:Duration

    fun generateToken(userDetails: UserDetails):String{
        var claims= mutableMapOf<String,Any>()
        claims["departments"] = userDetails.authorities.filterIndexed{index,_->index!=0}
            .map { it.authority }
        claims["role"] = userDetails.authorities.first().authority
        val date= Date()
        val expiredDate=Date(date.time+jwtLifetime.toMillis())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(date)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256,jwtSecret)
            .compact()
    }

    fun getLoginFromToken(token:String)=getClaims(token).subject

    fun getRoleFromToken(token: String)=getClaims(token).get("role",String::class.java)

    fun getDepartmentsFromToken(token:String)=getClaims(token).get("departments",List::class.java)

    fun getClaims(token:String)=Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
}