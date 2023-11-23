package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Company
import com.ClinicBackend.demo.Entities.Department
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepos: JpaRepository<Department, Long> {
    fun findByDepartmentNameAndCompany(departmentName:String,company: Company):Department?

    @EntityGraph(attributePaths = ["users"])
    fun findDepartmentWithUsersByDepartmentName(departmentName:String):Department?

    @Transactional
    fun deleteByDepartmentNameAndCompany(departmentName: String, company: Company)
}