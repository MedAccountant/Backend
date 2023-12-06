package com.ClinicBackend.demo.Entities.ManagePositions

import jakarta.persistence.*

@Entity
@Table(name="position_attributes")
open class PositionAttribute() {

    constructor(positionAttribute: PositionAttribute):this(){
        attributeName=positionAttribute.attributeName
        value=positionAttribute.value
        positionToPositionData=positionAttribute.positionToPositionData
        positionToCurrentPosition=positionAttribute.positionToCurrentPosition
        positionToUniquePosition=positionAttribute.positionToUniquePosition
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_attribute_id")
    open var positionAttributeId:Long?=null

    @Column(length = 50)
    open var attributeName: String? = null

    @Column(name = "value")
    open var value:String?=null

    @ManyToOne
    @JoinColumn(name = "position_data_id")
    open var positionToPositionData: PositionData?=null

    @ManyToOne
    @JoinColumn(name = "unique_position_id")
    open var positionToUniquePosition: UniquePosition?=null

    @ManyToOne
    @JoinColumn(name = "current_position_id")
    open var positionToCurrentPosition: CurrentPosition?=null



    override fun toString(): String {
        return "PositionAttribute(attributeName=$attributeName, value=$value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PositionAttribute) return false

        if (attributeName != other.attributeName) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = attributeName?.hashCode() ?: 0
        result = 31 * result + (value?.hashCode() ?: 0)
        return result
    }


}