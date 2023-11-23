package com.ClinicBackend.demo.Service

import com.ClinicBackend.demo.DAO.CompanyDAOImpl
import com.ClinicBackend.demo.DTO.CompanyDTO
import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.DTO.CreateDTOs.UserCreationDTO
import com.ClinicBackend.demo.DTO.SupplierDTO
import com.ClinicBackend.demo.DTO.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService {
    @Autowired
    lateinit private var companyDAOImpl: CompanyDAOImpl

    fun getCompanies() = companyDAOImpl.getCompanies()

    //fun getCompanyByName(companyDTO: CompanyDTO) = companyDAOImpl.getCompanyByName(companyDTO.companyName!!)//do i really need it

    fun createCompany(companyDTO: CompanyDTO) = companyDAOImpl.createCompany(companyDTO.makeCompanyFromDTO())

    fun deleteCompany(companyDTO: CompanyDTO){ companyDAOImpl.deleteCompany(companyDTO.companyName!!)}

    fun editCompany(oldCompanyDTO: CompanyDTO, newCompanyDTO: CompanyDTO) {
        companyDAOImpl.editCompany(oldCompanyDTO.companyName!!, newCompanyDTO.makeCompanyFromDTO())
    }
    //-----------------------------

    fun getDepartmentsOfCompany(companyName: String)=companyDAOImpl.getDepartmentsFromCompany(companyName!!)

    fun addDepartment(departmentDTO: DepartmentDTO, companyName:String):String?{
        var newDepartment=departmentDTO.makeDepartmentFromDTO()
        newDepartment.company=companyDAOImpl.getCompanyByName(companyName)
        companyDAOImpl.addDepartment(newDepartment)
        return departmentDTO.departmentName
    }

    //fun getDepartmentByName(departmentName:String) = companyDAOImpl.getDepartmentByName(departmentName)

    fun deleteDepartment(departmentDTO: DepartmentDTO, companyName: String) {
        companyDAOImpl.deleteDepartmentFromCompany(departmentDTO.departmentName!!,companyName)
    }

    fun editDepartment(oldDepartmentDTO: DepartmentDTO, newDepartmentDTO: DepartmentDTO,companyName: String){
        companyDAOImpl.editDepartment(oldDepartmentDTO.departmentName!!,newDepartmentDTO.makeDepartmentFromDTO(),companyName)
    }

    //-------------------------------------

    fun getUsersOfCompany(companyName: String)=companyDAOImpl.getAllUsersFromCompany(companyName)

    fun addUserToCompany(userCreationDTO: UserCreationDTO, companyName: String):String?{
        //println("userDTO: $userDTO")
        var newUser=userCreationDTO.makeUserFromDTO()
        newUser.departments=companyDAOImpl.getDepartmentsFromCompany(companyName)
            .filter {DepartmentDTO(it) in userCreationDTO.departments!!}.toMutableSet()
        companyDAOImpl.addUser(newUser)
        return newUser.nickname
    }

    fun deleteUserFromCompany(userDTO: UserDTO,companyName: String) {
        companyDAOImpl.deleteUser(userDTO.login!!)
    }

    fun editUser(oldUserLogin:String, newUserCreationDTO: UserCreationDTO, companyName: String){
        var newUser=newUserCreationDTO.makeUserFromDTO()
        newUser.departments=companyDAOImpl.getDepartmentsFromCompany(companyName)
            .filter {DepartmentDTO(it) in newUserCreationDTO.departments!!}.toMutableSet()
        companyDAOImpl.editUser(oldUserLogin,
            newUser,
            companyName)
    }

    //---------------------------------------

    fun getSuppliersOfCompany(companyName: String)=companyDAOImpl.getAllSuppliersFromCompany(companyName)

    fun addSupplierToCompany(supplierDTO: SupplierDTO, companyName: String):String?{
        //println("supplierDTO: $supplierDTO")
        var newSupplier=supplierDTO.makeSupplierFromDTO()
        newSupplier.departments=companyDAOImpl.getDepartmentsFromCompany(companyName)
            .filter {DepartmentDTO(it) in supplierDTO.departments!!}.toMutableSet()
        companyDAOImpl.addSupplier(newSupplier)
        return newSupplier.name
    }

    fun deleteSupplierFromCompany(supplierDTO: SupplierDTO,companyName: String) {
        companyDAOImpl.deleteSupplier(supplierDTO.email!!)
    }

    fun editSupplier(oldSupplierLogin:String, newSupplierCreationDTO: SupplierDTO, companyName: String){
        var newSupplier=newSupplierCreationDTO.makeSupplierFromDTO()
        newSupplier.departments=companyDAOImpl.getDepartmentsFromCompany(companyName)
            .filter {DepartmentDTO(it) in newSupplierCreationDTO.departments!!}.toMutableSet()
        companyDAOImpl.editSupplier(oldSupplierLogin,
            newSupplier,
            companyName)
    }

    //order management
    fun getAllOrdersFromCompanyDepartment(departmentName: String, companyName: String)=
        companyDAOImpl.getAllOrdersFromCompanyDepartment(departmentName,companyName)

    fun getOrderByName(orderName: String)=companyDAOImpl.getOrderByName(orderName)
}