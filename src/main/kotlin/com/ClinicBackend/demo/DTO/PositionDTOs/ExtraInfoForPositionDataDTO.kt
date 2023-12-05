package com.ClinicBackend.demo.DTO.PositionDTOs

import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.Entities.ManagePositions.PositionData

class ExtraInfoForPositionDataDTO() {
    var editedBy:String?=null
    var attributes:List<AttributeDTO> = mutableListOf<AttributeDTO>()
    var limits:List<LimitsDTO> = mutableListOf<LimitsDTO>()
    var departmentsWherePositionOccurs:List<DepartmentDTO> = mutableListOf<DepartmentDTO>()

    constructor(positionData: PositionData):this(){
        editedBy=positionData.editedBy!!.login
        attributes=positionData.attributes.map { AttributeDTO(it) }
        limits=positionData.limits.map { LimitsDTO(it) }
    }
    /*fun makePositionDataFromDTO(): User {
        var newPositionData=PositionData()
        newPositionData.
    }*/

    override fun toString():String{
        return  "{\n" +
                "\"editedBy\":$editedBy \n" +
                "\"attributes\":[\n" +
                attributes.joinToString { ",\n " }+
                "],\n"+
                "\"limits\":[\n" +
                limits.joinToString { ",\n " }+
                "]\n}"
    }
}