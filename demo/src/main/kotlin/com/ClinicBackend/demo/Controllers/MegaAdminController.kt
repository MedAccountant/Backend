package com.ClinicBackend.demo.Controllers

import com.ClinicBackend.demo.DTO.CompanyDTO
import com.ClinicBackend.demo.Service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/sys_admin")
class MegaAdminController {
    @Autowired
    lateinit var companyService: CompanyService

    @GetMapping("/companies")
    fun getCompanies():ResponseEntity<List<CompanyDTO>>{
        /*println("companies:------------")
        companyService.getCompanies().toList().forEach { println("${it.companyName}") }*/
        val companyDTOs=companyService.getCompanies().toList().map { CompanyDTO(it) }
        /*println("----------")
        companyDTOs.forEach { print("${it::class.simpleName} ,") }*/
        return ResponseEntity.ok(companyDTOs)
    }

    @PostMapping("/companies")
    fun createCompany(@RequestBody companyDTO: CompanyDTO):ResponseEntity<List<CompanyDTO>>{
        companyService.createCompany(companyDTO)
        val companyDTOs=companyService.getCompanies().toList().map { CompanyDTO(it) }
        return ResponseEntity.ok(companyDTOs)
    }

    @DeleteMapping("/companies")
    fun deleteCompany(@RequestBody companyDTOtoDelete:CompanyDTO):ResponseEntity<List<CompanyDTO>>{
        //println("company to delete: ${companyDTOtoDelete.companyName}")
        companyService.deleteCompany(companyDTOtoDelete)
        val companyDTOs=companyService.getCompanies().toList().map { CompanyDTO(it) }
        return ResponseEntity.ok(companyDTOs)
    }

    @PutMapping("/companies")
    fun editCompany(@RequestBody companyDTOsToUpdate:List<CompanyDTO>):ResponseEntity<List<CompanyDTO>>{
        println("old company name: ${companyDTOsToUpdate[0].companyName}, new: ${companyDTOsToUpdate[1].companyName}")
        companyService.editCompany(companyDTOsToUpdate[0],companyDTOsToUpdate[1])
        val companyDTOs=companyService.getCompanies().toList().map { CompanyDTO(it) }
        return ResponseEntity.ok(companyDTOs)
    }

}