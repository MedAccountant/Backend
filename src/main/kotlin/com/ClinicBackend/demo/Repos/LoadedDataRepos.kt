package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.Company
import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageLoadedData.LoadedData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoadedDataRepos: JpaRepository<LoadedData, Long> {
    fun findByLinkToFile(linkToFile:String):LoadedData?

    //@EntityGraph(attributePaths = ["positions"])
    //fun findAllLoadedDataWithPositionsByDepartment(department: Department):List<LoadedData>
}