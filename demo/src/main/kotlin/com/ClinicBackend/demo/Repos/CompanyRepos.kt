package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepos: JpaRepository<Company, Long> {
    fun findByCompanyName(companyName:String):Company?
}