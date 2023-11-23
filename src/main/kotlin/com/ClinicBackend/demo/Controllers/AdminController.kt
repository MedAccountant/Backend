package com.ClinicBackend.demo.Controllers

import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.DTO.CreateDTOs.UserCreationDTO
import com.ClinicBackend.demo.DTO.SupplierDTO
import com.ClinicBackend.demo.DTO.UserDTO
import com.ClinicBackend.demo.Service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/{companyName}/admin")
class AdminContoller {
    @Autowired
    lateinit var companyService: CompanyService
    //department management
    @GetMapping("/departments")
    fun getDepartments(@PathVariable companyName:String):ResponseEntity<List<DepartmentDTO>>{
        val departmentDTOs=companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }

    @PostMapping("/departments")
    fun createDepartment(@RequestBody departmentDTO: DepartmentDTO,
                         @PathVariable companyName:String):ResponseEntity<List<DepartmentDTO>>{
        companyService.addDepartment(departmentDTO,companyName)
        val departmentDTOs=companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }

    @DeleteMapping("/departments")
    fun deleteDepartment(@RequestBody departmentDTO: DepartmentDTO,
                         @PathVariable companyName:String):ResponseEntity<List<DepartmentDTO>>{
        companyService.deleteDepartment(departmentDTO,companyName)
        val departmentDTOs=companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }

    @PutMapping("/departments")
    fun editDepartment(@RequestBody departmentDTOsToUpdate:List<DepartmentDTO>,
                       @PathVariable companyName:String):ResponseEntity<List<DepartmentDTO>>{
        //println("old company name: ${companyDTOsToUpdate[0].companyName}, new: ${companyDTOsToUpdate[1].companyName}")
        companyService.editDepartment(departmentDTOsToUpdate[0],departmentDTOsToUpdate[1],companyName)
        val departmentDTOs=companyService.getDepartmentsOfCompany(companyName).map { DepartmentDTO(it) }
        return ResponseEntity.ok(departmentDTOs)
    }

    //user management
    @GetMapping("/users")
    fun getUsers(@PathVariable companyName:String):ResponseEntity<List<UserDTO>>{
        val userDTOs=companyService.getUsersOfCompany(companyName).map { UserDTO(it) }
        return ResponseEntity.ok(userDTOs)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody userCreationDTO: UserCreationDTO,
                   @PathVariable companyName:String):ResponseEntity<List<UserDTO>>{
        companyService.addUserToCompany(userCreationDTO,companyName)
        val userDTOs=companyService.getUsersOfCompany(companyName).map { UserDTO(it) }
        return ResponseEntity.ok(userDTOs)
    }

    @DeleteMapping("/users")
    fun deleteUser(@RequestBody userDTO: UserDTO,
                   @PathVariable companyName:String):ResponseEntity<List<UserDTO>>{
        companyService.deleteUserFromCompany(userDTO,companyName)
        val userDTOs=companyService.getUsersOfCompany(companyName).map { UserDTO(it) }
        return ResponseEntity.ok(userDTOs)
    }

    @PutMapping("/users")
    fun editUser(@RequestParam("old_user_login") oldUserLogin:String,
                 @RequestBody newUserCreationDTO: UserCreationDTO,
                 @PathVariable companyName:String):ResponseEntity<List<UserDTO>>{
        companyService.editUser(oldUserLogin, newUserCreationDTO, companyName)
        val userDTOs=companyService.getUsersOfCompany(companyName).map { UserDTO(it) }
        return ResponseEntity.ok(userDTOs)
    }

    //suppliers management
    @GetMapping("/suppliers")
    fun getSuppliers(@PathVariable companyName:String):ResponseEntity<List<SupplierDTO>>{
        val supplierDTOs=companyService.getSuppliersOfCompany(companyName).map { SupplierDTO(it) }
        return ResponseEntity.ok(supplierDTOs)
    }

    @PostMapping("/suppliers")
    fun createSupplier(@RequestBody supplierCreationDTO: SupplierDTO,
                   @PathVariable companyName:String):ResponseEntity<List<SupplierDTO>>{
        companyService.addSupplierToCompany(supplierCreationDTO,companyName)
        val supplierDTOs=companyService.getSuppliersOfCompany(companyName).map { SupplierDTO(it) }
        return ResponseEntity.ok(supplierDTOs)
    }

    @DeleteMapping("/suppliers")
    fun deleteSupplier(@RequestBody supplierDTO: SupplierDTO,
                   @PathVariable companyName:String):ResponseEntity<List<SupplierDTO>>{
        companyService.deleteSupplierFromCompany(supplierDTO,companyName)
        val supplierDTOs=companyService.getSuppliersOfCompany(companyName).map { SupplierDTO(it) }
        return ResponseEntity.ok(supplierDTOs)
    }

    @PutMapping("/suppliers")
    fun editSupplier(@RequestParam("old_supplier_email") oldSupplierLogin:String,
                 @RequestBody newSupplierCreationDTO: SupplierDTO,
                 @PathVariable companyName:String):ResponseEntity<List<SupplierDTO>>{
        companyService.editSupplier(oldSupplierLogin, newSupplierCreationDTO, companyName)
        val supplierDTOs=companyService.getSuppliersOfCompany(companyName).map { SupplierDTO(it) }
        return ResponseEntity.ok(supplierDTOs)
    }
}