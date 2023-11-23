package com.ClinicBackend.demo.Entities.ManagePositions

import jakarta.persistence.*

@Entity
@Table(name="unique_positions")
open class UniquePositions() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unique_position_id")
    open var uniquePositionId: Long? = null

    @Column(name = "position_name", unique = true, length = 60)
    open var positionName: String? = null

    @OneToMany(mappedBy = "positionToUniquePositions")
    open var attributes= mutableSetOf<PositionAttributes>()

    @OneToMany(mappedBy = "positionToUniquePositions")
    open var limits= mutableSetOf<Limits>()
}