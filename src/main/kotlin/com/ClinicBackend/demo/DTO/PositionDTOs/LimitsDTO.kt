package com.ClinicBackend.demo.DTO.PositionDTOs

import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.Entities.ManagePositions.Limits
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.*

class LimitsDTO() {
    var min:Long?=null
    var max:Long?=null
    @JsonFormat(pattern="yyyy-MM-dd")
    var startDate: LocalDate?=null
    @JsonFormat(pattern="yyyy-MM-dd")
    var endDate:LocalDate?=null
    var department:DepartmentDTO?=null

    constructor(limits: Limits):this(){
        min=limits.min
        max=limits.max
        startDate=limits.startDate
        endDate=limits.endDate
    }
    fun makeLimitsFromDTO(): Limits {
        var newLimits=Limits()
        newLimits.min=min
        newLimits.max=max
        newLimits.startDate=startDate
        newLimits.endDate=endDate
        return newLimits
    }

    override fun toString():String{
        return  "{\n" +
                "\"min\":$min \n" +
                "\"max\":$max \n" +
                "\"startDate\":$startDate \n" +
                "\"endDate\":$endDate \n" +
                "\"department\":${department!!.departmentName} \n" +
                "\n}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LimitsDTO) return false

        if (min != other.min) return false
        if (max != other.max) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (department != other.department) return false

        return true
    }

    override fun hashCode(): Int {
        var result = min?.hashCode() ?: 0
        result = 31 * result + (max?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + (department?.hashCode() ?: 0)
        return result
    }


}