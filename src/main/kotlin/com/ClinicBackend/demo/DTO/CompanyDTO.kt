package com.ClinicBackend.demo.DTO

import com.ClinicBackend.demo.Entities.Company
import com.fasterxml.jackson.annotation.JsonIgnore

class CompanyDTO() {
    var companyName:String?=null
    constructor(company: Company):this(){
        companyName=company.companyName
    }
    fun makeCompanyFromDTO():Company{
        var newCompany=Company()
        newCompany.companyName=companyName
        return newCompany
    }

    override fun toString()="{\n" +
            "\"companyName\":$companyName \n" +
            "}"
}