package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.ManagePositions.PositionAttribute
import com.ClinicBackend.demo.Entities.ManagePositions.UniquePosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UniquePositionRepos: JpaRepository<UniquePosition, Long> {
    //fun findByNameAndAttributes(name:String,attributes: List<PositionAttribute>): UniquePosition?
}