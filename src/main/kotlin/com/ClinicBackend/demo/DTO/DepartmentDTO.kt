package com.ClinicBackend.demo.DTO

import com.ClinicBackend.demo.Entities.Department

class DepartmentDTO() {
    var departmentName:String?=null
    var workingMarker:Boolean?=null
    constructor(department: Department):this(){
        departmentName=department.departmentName
        workingMarker=department.workingMarker
    }
    fun makeDepartmentFromDTO():Department{
        var newDepartment=Department()
        newDepartment.departmentName=departmentName
        newDepartment.workingMarker=workingMarker
        return newDepartment
    }

    override fun toString()="{\n" +
            "\"companyName\":$departmentName \n" +
            "}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DepartmentDTO) return false

        if (departmentName != other.departmentName) return false

        return true
    }

    override fun hashCode(): Int {
        return departmentName?.hashCode() ?: 0
    }

}

