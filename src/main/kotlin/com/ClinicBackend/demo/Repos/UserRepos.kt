package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageUsers.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepos: JpaRepository<User, Long> {
    fun findByLogin(login:String): User?
    fun findByDepartmentsIn(departmentsOfCompany: List<Department>):Iterable<User>
}