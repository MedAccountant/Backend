package com.ClinicBackend.demo.Entities

import com.ClinicBackend.demo.DTO.CompanyDTO
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name="companies")
open class Company() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    open var companyId:Long?=null

    @Column(name="company_name", unique = true, length = 60)
    open var companyName:String?=null

    @OneToMany(mappedBy = "company",cascade = [(CascadeType.ALL)])
    open var departments=mutableListOf<Department>()

    fun editCompany(newCompany: Company){
        companyName=newCompany.companyName
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Company) return false

        if (companyName != other.companyName) return false

        return true
    }

    override fun hashCode(): Int {
        return companyName?.hashCode() ?: 0
    }


}