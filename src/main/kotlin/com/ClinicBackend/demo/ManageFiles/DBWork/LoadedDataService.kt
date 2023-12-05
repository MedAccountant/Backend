package com.ClinicBackend.demo.ManageFiles.DBWork

import com.ClinicBackend.demo.DAO.CompanyDAOImpl
import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.DTO.PositionDTOs.AttributeDTO
import com.ClinicBackend.demo.DTO.PositionDTOs.ExtraInfoForPositionDataDTO
import com.ClinicBackend.demo.DTO.PositionDTOs.LimitsDTO
import com.ClinicBackend.demo.DTO.PositionDTOs.PositionDataDTO
import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageLoadedData.DocType
import com.ClinicBackend.demo.Entities.ManageLoadedData.LoadedData
import com.ClinicBackend.demo.Entities.ManagePositions.*
import com.ClinicBackend.demo.Repos.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.io.FileInputStream

@Service
class LoadedDataService {
    @Autowired
    private lateinit var companyDAOImpl: CompanyDAOImpl

    @Autowired
    private lateinit var loadedDataDAO: LoadedDataDAO

    fun processFile(linkToFile:String, docType: DocType, companyName:String){
        loadedDataDAO.startProcessingDependingOnDocType(linkToFile,docType,companyName)
    }

    fun getNewPositionsOfDepartmentsList(departments: List<Department>,
                                         companyName: String):List<PositionData>{
        return loadedDataDAO.getNewPositionsOfDepartmentsList(departments.toSet()).distinct()
    }

    fun getNewPositionExtraData(positionDataId:Long, departments: List<Department>, companyName: String)
    :ExtraInfoForPositionDataDTO{
        val requiredPositionData=/*positionDataDTO.makePositionDataFromDTO()*/
            loadedDataDAO.getPositionDataById(positionDataId)
        val equalPositions=loadedDataDAO.getEqualPositionsData(requiredPositionData,departments)
        var newExtraInfoForPositionDataDTO=ExtraInfoForPositionDataDTO()
        val limitDTOs=equalPositions.flatMap {
            position-> position.limits.map { limit->
                LimitsDTO(limit).also { it.department=DepartmentDTO(position.loadedData!!.department!!) }
            }
        }.distinct()
        newExtraInfoForPositionDataDTO.limits=limitDTOs
        newExtraInfoForPositionDataDTO.attributes=requiredPositionData.attributes.map { AttributeDTO(it) }
        newExtraInfoForPositionDataDTO.departmentsWherePositionOccurs=equalPositions
            .map { DepartmentDTO(it.loadedData!!.department!!) }.distinct()
        return newExtraInfoForPositionDataDTO
    }

    fun updateLimitsAndAttributesPositionData(positionDataId:Long,
                                  departments: List<Department>,
                                  newExtraInfoForPositionDataDTO: ExtraInfoForPositionDataDTO,
                                  companyName: String):Boolean{
        val requiredPositionData=/*positionDataDTO.makePositionDataFromDTO()*/
            loadedDataDAO.getPositionDataById(positionDataId)
        val equalPositions=loadedDataDAO.getEqualPositionsData(requiredPositionData,departments)
        for(department in departments){
            val limitsForDepartment=newExtraInfoForPositionDataDTO.limits
                .filter { getDepartmentFromCompany(it.department!!.departmentName!!,companyName)==department }
                .map { it.makeLimitsFromDTO() }
            val positionDataFromDepartment=equalPositions.find { it.loadedData!!.department==department }!!
            limitsForDepartment.forEach{it.positionToPositionData=positionDataFromDepartment}
            positionDataFromDepartment.limits=limitsForDepartment.toMutableSet()
        }
        if(requiredPositionData.attributes !=
            newExtraInfoForPositionDataDTO.attributes.map { it.makeAttributeFromDTO() }.toSet()){
            equalPositions.forEach { position->
                position.attributes=newExtraInfoForPositionDataDTO.attributes
                    .map { attribute->
                        attribute.makeAttributeFromDTO().also { it.positionToPositionData = position} }.toMutableSet() }
        }
        println("equals: ======================")
        equalPositions.forEach { println(it) }
        loadedDataDAO.savePositionsData(equalPositions)
        return true
    }

    fun getCurrentPositionsOfDepartment(department: Department):List<CurrentPosition>{
        return department.currentPositions.toList()
    }

    fun getDepartmentFromCompany(departmentName:String,companyName: String)=
        companyDAOImpl.getDepartmentByNameAndCompany(departmentName,companyName)
}