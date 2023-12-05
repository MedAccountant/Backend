package com.ClinicBackend.demo.DTO.PositionDTOs

import com.ClinicBackend.demo.Entities.ManagePositions.PositionAttribute
import com.ClinicBackend.demo.Entities.ManageUsers.Role
import com.ClinicBackend.demo.Entities.ManageUsers.User

class AttributeDTO() {
    var attributeName:String?=null
    var value:String?=null

    constructor(attribute: PositionAttribute):this(){
        attributeName=attribute.attributeName
        value=attribute.value
    }
    fun makeAttributeFromDTO(): PositionAttribute {
        var newAttribute=PositionAttribute()
        newAttribute.attributeName=attributeName
        newAttribute.value=value
        return newAttribute
    }

    override fun toString()="{\n" +
                "\"attributeName\":$attributeName \n" +
                "\"value\":$value \n}"
}