package com.ClinicBackend.demo.Entities.ManagePositions

import com.ClinicBackend.demo.Entities.ManageLoadedData.LoadedData
import jakarta.persistence.*

@Entity
@Table(name="position_attributes")
open class PositionAttributes() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_attribute_id")
    open var positionAttributeId:Long?=null

    @Column(length = 50, unique = true)
    open var attributeName: String? = null

    @Column(name = "value")
    open var value:String?=null

    @ManyToOne
    @JoinColumn(name = "positions_data_id")
    open var positionToPositionsData: PositionData?=null

    @ManyToOne
    @JoinColumn(name = "unique_positions_id")
    open var positionToUniquePositions: UniquePositions?=null

    @ManyToOne
    @JoinColumn(name = "current_positions_id")
    open var positionToCurrentPositions: CurrentPosition?=null
}