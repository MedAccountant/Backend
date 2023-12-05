package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageLoadedData.DocType
import com.ClinicBackend.demo.Entities.ManageLoadedData.LoadedData
import com.ClinicBackend.demo.Entities.ManagePositions.CurrentPosition
import com.ClinicBackend.demo.Entities.ManagePositions.PositionAttribute
import com.ClinicBackend.demo.Entities.ManagePositions.PositionData
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PositionDataRepos: JpaRepository<PositionData, Long> {
    fun findAllByProcessedMarkerEqualsAndLoadedData_DocumentTypeInAndLoadedData_DepartmentIn
                (processedMarker:Boolean, docTypeSet: Set<DocType>,departments: Set<Department>):List<PositionData>

   //@EntityGraph(attributePaths = ["attributes","limits"])
    fun findAllByProcessedMarkerEqualsAndNameAndAttributes_AttributeNameInAndLoadedData_DepartmentIn
                (processedMarker: Boolean,name:String,namesOfAttributes: Set<String>,departments: Set<Department>):List<PositionData>

    //@EntityGraph(attributePaths = ["attributes","limits"])
    /*fun findAllByProcessedMarkerEqualsAndNameAndAttributesInAndLoadedData_DepartmentIn
                (processedMarker: Boolean,name:String,attributes: Set<PositionAttribute>,departments: Set<Department>):List<PositionData>

    fun findAllByProcessedMarkerEqualsAndNameAndAttributesIn
                (processedMarker: Boolean,name:String,attributes: Set<PositionAttribute>):List<PositionData>

    fun findAllByProcessedMarkerEqualsAndName
                (processedMarker: Boolean,name:String):List<PositionData>*/
}