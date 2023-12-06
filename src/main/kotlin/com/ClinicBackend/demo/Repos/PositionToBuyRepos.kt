package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageLoadedData.DocType
import com.ClinicBackend.demo.Entities.ManagePositions.CurrentPosition
import com.ClinicBackend.demo.Entities.ManagePositions.PositionData
import com.ClinicBackend.demo.Entities.ManagePositions.PositionToBuy
import org.springframework.data.jpa.repository.JpaRepository

interface PositionToBuyRepos: JpaRepository<PositionToBuy, Long> {
    fun findByCurrentPosition(currentPosition: CurrentPosition):PositionToBuy?
}