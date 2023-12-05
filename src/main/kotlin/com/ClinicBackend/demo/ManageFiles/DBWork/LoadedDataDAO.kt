package com.ClinicBackend.demo.ManageFiles.DBWork

import com.ClinicBackend.demo.DTO.PositionDTOs.ExtraInfoForPositionDataDTO
import com.ClinicBackend.demo.DTO.PositionDTOs.PositionDataDTO
import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageLoadedData.DocType
import com.ClinicBackend.demo.Entities.ManageLoadedData.LoadedData
import com.ClinicBackend.demo.Entities.ManagePositions.*
import com.ClinicBackend.demo.ManageFiles.Exceptions.StorageException
import com.ClinicBackend.demo.Repos.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.io.FileInputStream

@Service
class LoadedDataDAO {

    @Autowired
    private lateinit var loadedDataRepos: LoadedDataRepos

    @Autowired
    private lateinit var departmentRepos: DepartmentRepos

    @Autowired
    private lateinit var companyRepos: CompanyRepos

    @Autowired
    private lateinit var userRepos:UserRepos

    @Autowired
    private lateinit var currentPositionRepos: CurrentPositionRepos

    @Autowired
    private lateinit var uniquePositionRepos: UniquePositionRepos

    @Autowired
    private lateinit var positionDataRepos: PositionDataRepos

    fun startProcessingDependingOnDocType(linkToFile:String, docType: DocType, companyName:String){
        var loadedData=LoadedData()
        loadedData.documentType=docType
        loadedData.linkToFile=linkToFile
        val departmentName=SecurityContextHolder.getContext().authentication.authorities.first().authority
        println("department: $departmentName")
        loadedData.department=departmentRepos.findByDepartmentNameAndCompany(
            departmentName,
            companyRepos.findByCompanyName(companyName)!!)
        println("founded: ${loadedData.department?.departmentName}")
        loadedData.sender=userRepos.findByLogin(SecurityContextHolder.getContext().authentication.name)
        when(docType){
            DocType.ActualData->processActual(loadedData)
            DocType.WriteOffData->processWriteOff(loadedData)
            DocType.AdmissionData->processAdmission(loadedData)
        }
        loadedDataRepos.save(loadedData)
    }

    fun processActual(loadedData: LoadedData){
        val fileInStream = FileInputStream(loadedData.linkToFile!!)
        val reader=fileInStream.bufferedReader()
        var possibleAttributes:List<String> = reader.readLine().split('|').map{it.trim(' ')}
        var positionRows=reader.readLines().map { it.split('|').map{it.trim(' ')} }
        val indOfCountColumn=possibleAttributes.indexOf("Количество")
        //println(possibleAttributes.joinToString(","))
        //positionRows.forEach { println(it.joinToString(",")) }
        for(row in positionRows){
            var newPositionData=PositionData()
            newPositionData.name=row[0]
            newPositionData.loadedData=loadedData
            newPositionData.count=row[indOfCountColumn].toDouble().toLong()
            newPositionData.attributes=row.mapIndexed { index, value ->
                PositionAttribute().also {
                    it.attributeName=possibleAttributes[index]
                    it.value=value
                    it.positionToPositionData=newPositionData
                } }.filterIndexed {
                    index, attr -> index!=0 && index!=indOfCountColumn && attr.value!=""
            }.toMutableSet()
            //find position in current positions
            //val inCurrentPositions=currentPositionRepos.findAll(Example.of(CurrentPosition(newPositionData)))
            println("maybe error")
            val inCurrentPositions=currentPositionRepos
                .findWithAttributesAndLimitsByNameAndAttributes_AttributeNameInAndDepartmentIn(newPositionData.name!!,
                    newPositionData.attributes.map { it.attributeName!! }.toSet(),
                    setOf(loadedData.department!!)
                )
            println("maybe error 2")
            println("founded in currentPositions: ${inCurrentPositions.map { it.name }.joinToString(",")}")
            if(inCurrentPositions.isNotEmpty()){
                //get limits from most suitable current position
                val possibleCurrentLimits=inCurrentPositions.first().limits
                val newLimits= possibleCurrentLimits.map { Limits(it,newPositionData) }.toMutableSet()
                newPositionData.limits=newLimits
                newPositionData.uniqueMarker=false
                newPositionData.processedMarker=true
                newPositionData.processedBy=loadedData.sender
                //update count of position in current positions table
                inCurrentPositions.first().count=newPositionData.count
                println("limits from current pos")
            }
            else{
                val inUniquePositions=uniquePositionRepos.findAll(Example.of(UniquePosition(newPositionData)))
                if(inUniquePositions.isNotEmpty()){
                    //get limits from unique position
                    val possibleUniqueLimits=inUniquePositions.first().limits
                    val newLimits= possibleUniqueLimits.map { Limits(it,newPositionData) }.toMutableSet()
                    newPositionData.limits=newLimits
                    newPositionData.uniqueMarker=true
                    newPositionData.processedMarker=false
                    println("limits from unique pos")
                }
                else{
                    //position is unique
                    newPositionData.uniqueMarker=true
                    newPositionData.processedMarker=false
                    val inPositionData=getEqualPositionsData(
                        newPositionData,
                        listOf(loadedData.department!!)
                    )
                    inPositionData.forEach {
                        it.processedMarker=true
                        /*if(loadedData.documentType!=DocType.ActualData)
                        when(it.loadedData!!.documentType){
                            DocType.ActualData->newCount=it.count!!
                            DocType.WriteOffData->throw StorageException("found write-off file for new position while counting")
                            DocType.AdmissionData->newCount+=it.count!!
                            null -> throw StorageException("found file with null document type while counting")
                        }*/
                    }
                    println("limits from unique pos")
                }
            }
            println(newPositionData)
            println("=====================================")
            loadedData.positions.add(newPositionData)
        }
        fileInStream.close()
    }

    fun processWriteOff(loadedData: LoadedData){
        TODO("Realize it")
    }

    fun processAdmission(loadedData: LoadedData){
        TODO("Realize it")
    }

    fun getNewPositionsOfDepartmentsList(departments: Set<Department>):List<PositionData>{
       /* return positionDataRepos.findByProcessedMarkerEquals(false)
            .filter { it.loadedData!!.documentType==DocType.ActualData&&
                    it.loadedData!!.department in departments }*/ //works right
        //return loadedDataRepos.findAllLoadedDataWithPositionsByDepartment(department)

        return positionDataRepos.findAllByProcessedMarkerEqualsAndLoadedData_DocumentTypeInAndLoadedData_DepartmentIn(false,
            setOf(DocType.ActualData,DocType.AdmissionData),departments
        )
    }

    fun getEqualPositionsData(positionData:PositionData,departments: List<Department>)=positionDataRepos
            /*.findWithAttributesAndLimitsByProcessedMarkerEqualsNameAndAttributes_AttributeNameInAndDepartmentIn(
                false,
                positionData.name!!,
                positionData.attributes.map{it.attributeName!!}.toSet(),
                departments.toSet())*/
            .findAllByProcessedMarkerEqualsAndNameAndAttributes_AttributeNameInAndLoadedData_DepartmentIn(
                false,
                positionData.name!!,
                positionData.attributes.map { it.attributeName!! }.toSet(),
                departments.toSet()).filter {it.attributes==positionData.attributes}

    fun getCurrentPositionsOfDepartmentsList(departments: List<Department>):List<CurrentPosition>{
        return departments.flatMap {it.currentPositions.toList()}
        //return loadedDataRepos.findAllLoadedDataWithPositionsByDepartment(department)
    }

    fun getPositionDataById(id:Long)=positionDataRepos.findById(id).orElseThrow()

    fun savePositionsData(positions:List<PositionData>){
        positions.forEach { positionDataRepos.saveAll(positions) }
    }
}