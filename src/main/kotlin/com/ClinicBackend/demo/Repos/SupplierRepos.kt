package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.Supplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepos: JpaRepository<Supplier, Long> {
    fun findByEmail(login:String): Supplier?
    fun findByDepartmentsIn(departmentsOfCompany: List<Department>):Iterable<Supplier>
}