package com.ClinicBackend.demo.Entities.ManagePositions

import jakarta.persistence.*

@Entity
@Table(name="current_positions")
open class CurrentPosition() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unique_position_id")
    open var currentPositionId: Long? = null

    @Column(name = "position_name", unique = true, length = 60)
    open var name: String? = null

    @Column(name = "count")
    open var count: Long? = null

    @OneToMany(mappedBy = "positionToCurrentPositions")
    open var attributes= mutableSetOf<PositionAttributes>()

    @OneToMany(mappedBy = "positionToCurrentPositions")
    open var limits= mutableSetOf<Limits>()

//    @OneToOne(mappedBy = "currentPosition")
//    open var positionToBuy:PositionToBuy?=null
}