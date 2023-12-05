package com.ClinicBackend.demo.DTO.PositionDTOs

import com.ClinicBackend.demo.Entities.ManagePositions.PositionAttribute
import com.ClinicBackend.demo.Entities.ManagePositions.PositionData

class PositionDataDTO() {
    var positionDataId:Long?=null
    var name: String? = null
    var editedMarker:Boolean=false
    var attributeNames:List<String> = mutableListOf<String>()

    constructor(positionData: PositionData):this(){
        positionDataId=positionData.positionDataId
        name=positionData.name
        editedMarker=positionData.editedMarker
        attributeNames=positionData.attributes.map { it.attributeName!! }
    }
    fun makePositionDataFromDTO(): PositionData {
        var newPositionData=PositionData()
        newPositionData.name=name
        newPositionData.attributes=attributeNames
            .map {attributeName-> PositionAttribute().also {
                    it.attributeName=attributeName
                    it.positionToPositionData=newPositionData
                }
            }.toMutableSet()
        return newPositionData
    }

    override fun toString():String{
        return  "{\n" +
                "\"name\":$name \n" +
                "\"edited\":$editedMarker \n" +
                "\"attributes\":[\n" +
                attributeNames.joinToString { ", " }+
                " ]\n}"
    }
}