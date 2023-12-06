package com.ClinicBackend.demo.Entities.ManagePositions

import jakarta.persistence.*


@Entity
@Table(name="positions_to_buy")
open class PositionToBuy() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_to_buy_id")
    open var positionToBuyId: Long? = null

    @Column(name = "count_to_buy")
    open var countToBuy: Long? = null

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "currentPositionId")
    open var currentPosition:CurrentPosition? = null
}