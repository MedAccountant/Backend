package com.ClinicBackend.demo.Security.UserDetailService

import com.ClinicBackend.demo.DTO.CreateDTOs.UserCreationDTO
import com.ClinicBackend.demo.Repos.UserRepos
import com.ClinicBackend.demo.Service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService:UserDetailsService {
    @Autowired
    lateinit private var userRepos: UserRepos

    override fun loadUserByUsername(username: String?): UserDetails {
        val user=userRepos.findByLogin(username!!)!!
        return User(user.login,
            user.password,
            user.departments.map { SimpleGrantedAuthority(it.departmentName) }.toMutableList()
                +mutableListOf(SimpleGrantedAuthority(user.role!!.name))
        )
    }

    fun createUser(@Autowired companyService: CompanyService, userCreationDTO: UserCreationDTO, companyName:String="Vista"){
        companyService.addUserToCompany(userCreationDTO,companyName)
    }
}