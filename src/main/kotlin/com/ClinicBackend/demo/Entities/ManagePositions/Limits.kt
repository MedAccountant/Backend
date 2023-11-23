package com.ClinicBackend.demo.Entities.ManagePositions

import jakarta.persistence.*

@Entity
@Table(name="limits")
open class Limits() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "limit_id")
    open var limitId:Long?=null

    @Column()
    open var min:Long?=null

    @Column()
    open var max:Long?=null

    @Column(name = "start_date", length = 10)
    open var startDate:String?=null

    @Column(name = "end_date", length = 10)
    open var endDate:String?=null

    @ManyToOne
    @JoinColumn(name = "position_data_id")
    open var positionToPositionsData: PositionData?=null

    @ManyToOne
    @JoinColumn(name = "unique_positions_id")
    open var positionToUniquePositions: UniquePositions?=null

    @ManyToOne
    @JoinColumn(name = "current_positions_id")
    open var positionToCurrentPositions: CurrentPosition?=null
}