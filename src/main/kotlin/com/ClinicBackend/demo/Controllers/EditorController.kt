package com.ClinicBackend.demo.Controllers

import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.DTO.PositionDTOs.ExtraInfoForPositionDataDTO
import com.ClinicBackend.demo.DTO.PositionDTOs.PositionDataDTO
import com.ClinicBackend.demo.ManageFiles.DBWork.LoadedDataService
import com.ClinicBackend.demo.Service.CompanyService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/{companyName}/editor")
class EditorController {
    @Autowired
    lateinit var loadedDataService: LoadedDataService

    @Autowired
    lateinit var companyService: CompanyService

    @Operation(description = "Returns new positions for one department")
    @GetMapping("/{departmentName}/new_positions")
    fun getNewPositionsOneDep(@PathVariable companyName: String,
                        @PathVariable departmentName: String
                        /*@RequestBody departmentDTOs:List<DepartmentDTO>*/
    ): ResponseEntity<List<PositionDataDTO>> {
        val department=loadedDataService.getDepartmentFromCompany(departmentName,companyName)
        if(department==null) println("error: department not found")
        val positionDataDTOs=loadedDataService
            .getNewPositionsOfDepartmentsList(listOf(department!!),companyName)
            .map { PositionDataDTO(it) }
        return ResponseEntity.ok(positionDataDTOs)
    }

    @Operation(description = "Returns data for new position from one department")
    @GetMapping("/{departmentName}/new_position_info")
    fun getExtraInfoOneDep(@PathVariable companyName: String,
                           @PathVariable departmentName: String,
                           @RequestParam("newPositionId") positionDataId:Long
    ): ResponseEntity<ExtraInfoForPositionDataDTO> {
        val department=loadedDataService.getDepartmentFromCompany(departmentName,companyName)
        if(department==null) println("error: department not found")
        val extraData=loadedDataService
            .getNewPositionExtraData(positionDataId, listOf(department!!),companyName)
        return ResponseEntity.ok(extraData)
    }

    @Operation(description = "Returns new positions from user departments")
    @GetMapping("/new_positions")
    fun getNewPositions(@PathVariable companyName: String): ResponseEntity<List<PositionDataDTO>> {
        val departments=SecurityContextHolder.getContext().authentication.authorities
            .take(SecurityContextHolder.getContext().authentication.authorities.size-1).map {
                loadedDataService.getDepartmentFromCompany(it.authority,companyName)!!
            }
        if(departments.isEmpty()) println("error: departments not found")
        val positionDataDTOs=loadedDataService
            .getNewPositionsOfDepartmentsList(departments,companyName)
            .map { PositionDataDTO(it) }
        return ResponseEntity.ok(positionDataDTOs)
    }

    @Operation(description = "Returns data for new position from departments in area of responsibility of user")
    @GetMapping("/new_position_info")
    fun getExtraInfo(@PathVariable companyName: String,
                           @RequestParam("newPositionId") positionDataId:Long
    ): ResponseEntity<ExtraInfoForPositionDataDTO> {
        val departments=SecurityContextHolder.getContext().authentication.authorities
            .take(SecurityContextHolder.getContext().authentication.authorities.size-1).map {
                loadedDataService.getDepartmentFromCompany(it.authority,companyName)!!
            }
        //println("===================")
        //departments.forEach { println("$it\n=====================") }
        if(departments.isEmpty()) println("error: departments not found")
        val extraData=loadedDataService
            .getNewPositionExtraData(positionDataId, departments,companyName)
        return ResponseEntity.ok(extraData)
    }

    @Operation(description = "Update limits and attributes to actual using ExtraInfoDTO")
    @PutMapping("/{departmentName}/new_position_info")
    fun editExtraInfoOneDep(@PathVariable companyName: String,
                           @PathVariable departmentName: String,
                           @RequestParam("newPositionId") positionDataId:Long,
                            @RequestBody newExtraInfoForPositionDataDTO: ExtraInfoForPositionDataDTO
    ): ResponseEntity<ExtraInfoForPositionDataDTO> {
        newExtraInfoForPositionDataDTO.editedBy=SecurityContextHolder.getContext().authentication.name
        val department=loadedDataService.getDepartmentFromCompany(departmentName,companyName)
        if(department==null) println("error: department not found")
        loadedDataService
            .updateLimitsAndAttributesPositionData(positionDataId, listOf(department!!),newExtraInfoForPositionDataDTO,companyName)
        return ResponseEntity.ok(newExtraInfoForPositionDataDTO)
    }

    @Operation(description = "Returns data for new position from departments in area of responsibility of user")
    @PutMapping("/new_position_info")
    fun editExtraInfo(@PathVariable companyName: String,
                           @RequestParam("newPositionId") positionDataId:Long,
                      @RequestBody newExtraInfoForPositionDataDTO: ExtraInfoForPositionDataDTO
    ): ResponseEntity<ExtraInfoForPositionDataDTO> {
        val positionUsedInDepartments=newExtraInfoForPositionDataDTO.departmentsWherePositionOccurs
            .map { loadedDataService.getDepartmentFromCompany(it.departmentName!!,companyName) }
        val departments=SecurityContextHolder.getContext().authentication.authorities
            .take(SecurityContextHolder.getContext().authentication.authorities.size-1).map {
                loadedDataService.getDepartmentFromCompany(it.authority,companyName)!!
            }.filter { it in positionUsedInDepartments }
        if(departments.isEmpty()) println("error: departments not found")
        loadedDataService
            .updateLimitsAndAttributesPositionData(positionDataId, departments,newExtraInfoForPositionDataDTO,companyName)
        return ResponseEntity.ok(newExtraInfoForPositionDataDTO)
    }

}