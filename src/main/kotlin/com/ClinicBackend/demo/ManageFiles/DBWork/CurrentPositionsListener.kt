package com.ClinicBackend.demo.ManageFiles.DBWork

import com.ClinicBackend.demo.Entities.ManagePositions.CurrentPosition
import jakarta.persistence.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component

@Component
class CurrentPositionsListener {

    private var currentPositionsService:CurrentPositionsService? = null

    @Autowired
    fun setCurrentPositionsService(currentPositionsService:CurrentPositionsService?) {
        this.currentPositionsService = currentPositionsService
    }

    @PostPersist
    @PostUpdate
    private fun checkPosition(currentPosition: CurrentPosition){
        currentPositionsService!!.checkPositionToBuy(currentPosition)
    }
}