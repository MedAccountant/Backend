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
import java.time.Period
import java.time.temporal.ChronoUnit

@Service
class LoadedDataService {
    @Autowired
    private lateinit var companyDAOImpl: CompanyDAOImpl

    @Autowired
    private lateinit var loadedDataDAO: LoadedDataDAO

    @Autowired
    private lateinit var userRepos: UserRepos

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
                                  companyName: String):ExtraInfoForPositionDataDTO{
        val requiredPositionData=/*positionDataDTO.makePositionDataFromDTO()*/
            loadedDataDAO.getPositionDataById(positionDataId)
        val equalPositions=loadedDataDAO.getEqualPositionsData(requiredPositionData,departments)
        for(department in departments){
            val limitsForDepartment=newExtraInfoForPositionDataDTO.limits
                .filter { getDepartmentFromCompany(it.department!!.departmentName!!,companyName)==department }
                .map { it.makeLimitsFromDTO() }
            val positionDataFromDepartment=equalPositions.find { it.loadedData!!.department==department }!!
            limitsForDepartment.forEach{it.positionToPositionData=positionDataFromDepartment}
            //positionDataFromDepartment.limits=limitsForDepartment.toMutableSet()
            positionDataFromDepartment.limits.clear()
            positionDataFromDepartment.limits.addAll(limitsForDepartment)
        }
        if(requiredPositionData.attributes !=
            newExtraInfoForPositionDataDTO.attributes.map { it.makeAttributeFromDTO() }.toSet()){
            equalPositions.forEach { position->
                position.attributes.clear()
                position.attributes.addAll(newExtraInfoForPositionDataDTO.attributes
                    .map { attribute->
                        attribute.makeAttributeFromDTO().also { it.positionToPositionData = position} })
                /*position.attributes=newExtraInfoForPositionDataDTO.attributes
                    .map { attribute->
                        attribute.makeAttributeFromDTO().also { it.positionToPositionData = position} }.toMutableSet()*/ }
        }
        if(departments.size==1)equalPositions.first().editedBy=userRepos.findByLogin(newExtraInfoForPositionDataDTO.editedBy!!)
        println("equals: ======================")
        equalPositions.forEach { println(it) }
        loadedDataDAO.savePositionsData(equalPositions)
        return getNewPositionExtraData(positionDataId,departments,companyName)
    }

    fun saveToCurrentPositions(positionDataId:Long,
                               departments: List<Department>,
                               companyName: String):Boolean{
        val requiredPositionData=loadedDataDAO.getPositionDataById(positionDataId)
        val equalPositions=loadedDataDAO.getEqualPositionsData(requiredPositionData,departments)
        for(position in equalPositions){
            val limits=position.limits.sortedBy { it.startDate }
            if(limits.size==0) throw SavePositionException("there is no limits")
            var daysCount:Int= ChronoUnit.DAYS.between(limits[0].startDate,limits[0].endDate).toInt()
            //println("daysCount: $daysCount, i: 0, ${limits[0].startDate.toString()}, ${limits[0].endDate.toString()}")
            for(i in 1 until limits.size){
                if(limits[i-1].endDate!!>=limits[i].startDate!!)
                    throw SavePositionException("there are limits with intersections of periods")
                daysCount+=ChronoUnit.DAYS.between(limits[i].startDate,limits[i].endDate).toInt()
                //println("daysCount: $daysCount, i: $i, ${limits[0].startDate.toString()}, ${limits[0].endDate.toString()}")
            }
            println("daysCount: $daysCount")
            if(daysCount<365)
                throw SavePositionException("there are days without limits for department: ${position.loadedData!!.department}")
        }
        loadedDataDAO.createCurrentPositions(equalPositions.map { CurrentPosition(it,it.loadedData!!.department!!) })
        equalPositions.forEach {
            it.processedMarker=true
            it.processedBy=userRepos.findByLogin(SecurityContextHolder.getContext().authentication.name)
        }
        println("current in departments: ")
        //departments.forEach { getCurrentPositionsOfDepartment(it).forEach { pos->println(pos) } }
        return true
    }


    fun getDepartmentFromCompany(departmentName:String,companyName: String)=
        companyDAOImpl.getDepartmentByNameAndCompany(departmentName,companyName)
}