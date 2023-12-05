package com.ClinicBackend.demo.Entities.ManagePositions

import jakarta.persistence.*

@Entity
@Table(name="limits")
open class Limits() {

    constructor(limit:Limits, positionData: PositionData):this(){
        min=limit.min
        max=limit.max
        startDate=limit.startDate
        endDate=limit.endDate
        positionToPositionData=positionData
    }

    constructor(limit:Limits, curPosition: CurrentPosition):this(){
        min=limit.min
        max=limit.max
        startDate=limit.startDate
        endDate=limit.endDate
        positionToCurrentPosition=curPosition
    }

    constructor(limit:Limits, uniquePosition: UniquePosition):this(){
        min=limit.min
        max=limit.max
        startDate=limit.startDate
        endDate=limit.endDate
        positionToUniquePosition=uniquePosition
    }

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
    open var positionToPositionData: PositionData?=null

    @ManyToOne
    @JoinColumn(name = "unique_position_id")
    open var positionToUniquePosition: UniquePosition?=null

    @ManyToOne
    @JoinColumn(name = "current_position_id")
    open var positionToCurrentPosition: CurrentPosition?=null
    override fun toString(): String {
        return "Limits(min=$min, max=$max, startDate=$startDate, endDate=$endDate)"
    }


}