package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManagePositions.CurrentPosition
import com.ClinicBackend.demo.Entities.ManagePositions.PositionAttribute
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CurrentPositionRepos: JpaRepository<CurrentPosition, Long> {
    @EntityGraph(attributePaths = ["attributes","limits"])
    fun findWithAttributesAndLimitsByNameAndAttributes_AttributeNameInAndDepartmentIn
                (name:String,namesOfAttributes: Set<String>,departments: Set<Department>):List<CurrentPosition>


}