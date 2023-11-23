package com.ClinicBackend.demo.Controllers

import com.ClinicBackend.demo.DTO.CreateDTOs.UserCreationDTO
import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.DTO.SupplierDTO
import com.ClinicBackend.demo.DTO.UserDTO
import com.ClinicBackend.demo.Service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/{companyName}/editor")
class EditorContoller {
    @Autowired
    lateinit var companyService: CompanyService

    //order management
    @GetMapping("/orders")
    fun getOrders(@PathVariable companyName: String): ResponseEntity<List<DepartmentDTO>> {
        val departmentDTOs = companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }

    @PostMapping("/orders")
    fun createOrder(
        @RequestBody departmentDTO: DepartmentDTO,
        @PathVariable companyName: String
    ): ResponseEntity<List<DepartmentDTO>> {
        companyService.addDepartment(departmentDTO, companyName)
        val departmentDTOs = companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }

    /*@DeleteMapping("/departments")
    fun deleteDepartment(
        @RequestBody departmentDTO: DepartmentDTO,
        @PathVariable companyName: String
    ): ResponseEntity<List<DepartmentDTO>> {
        companyService.deleteDepartment(departmentDTO, companyName)
        val departmentDTOs = companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }

    @PutMapping("/departments")
    fun editDepartment(
        @RequestBody departmentDTOsToUpdate: List<DepartmentDTO>,
        @PathVariable companyName: String
    ): ResponseEntity<List<DepartmentDTO>> {
        //println("old company name: ${companyDTOsToUpdate[0].companyName}, new: ${companyDTOsToUpdate[1].companyName}")
        companyService.editDepartment(departmentDTOsToUpdate[0], departmentDTOsToUpdate[1], companyName)
        val departmentDTOs = companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }*/
}