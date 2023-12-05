package com.ClinicBackend.demo.Entities.ManagePositions

import com.ClinicBackend.demo.Entities.Company
import com.ClinicBackend.demo.Entities.Department
import jakarta.persistence.*

@Entity
@Table(name="current_positions")
open class CurrentPosition() {

    constructor(positionData: PositionData):this(){
        name=positionData.name
        attributes=positionData.attributes
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "current_position_id")
    open var currentPositionId: Long? = null

    @Column(name = "position_name", unique = true, length = 60)
    open var name: String? = null

    @Column(name = "count")
    open var count: Long? = null

    @OneToMany(mappedBy = "positionToCurrentPosition", cascade=[CascadeType.ALL])
    open var attributes= mutableSetOf<PositionAttribute>()

    @OneToMany(mappedBy = "positionToCurrentPosition", cascade=[CascadeType.ALL])
    open var limits= mutableSetOf<Limits>()

    @ManyToOne
    @JoinColumn(name = "department_id")
    open var department: Department?=null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CurrentPosition) return false

        if (name != other.name) return false
        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + attributes.hashCode()
        return result
    }

//    @OneToOne(mappedBy = "currentPosition")
//    open var positionToBuy:PositionToBuy?=null


}