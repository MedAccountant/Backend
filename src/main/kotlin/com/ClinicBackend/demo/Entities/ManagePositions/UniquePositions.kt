package com.ClinicBackend.demo.Entities.ManagePositions

import jakarta.persistence.*

@Entity
@Table(name="unique_positions")
open class UniquePosition() {

    constructor(positionData: PositionData):this(){
        positionName=positionData.name
        attributes=positionData.attributes
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unique_position_id")
    open var uniquePositionId: Long? = null

    @Column(name = "position_name", unique = true, length = 60)
    open var positionName: String? = null

    @OneToMany(mappedBy = "positionToUniquePosition", cascade = [CascadeType.ALL])
    open var attributes= mutableSetOf<PositionAttribute>()

    @OneToMany(mappedBy = "positionToUniquePosition", cascade = [CascadeType.ALL])
    open var limits= mutableSetOf<Limits>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UniquePosition) return false

        if (positionName != other.positionName) return false
        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = positionName?.hashCode() ?: 0
        result = 31 * result + attributes.hashCode()
        return result
    }


}