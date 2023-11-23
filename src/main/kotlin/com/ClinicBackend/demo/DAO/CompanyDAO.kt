package com.ClinicBackend.demo.DAO

import com.ClinicBackend.demo.Entities.*
import com.ClinicBackend.demo.Entities.ManageOrders.Order
import com.ClinicBackend.demo.Entities.ManageUsers.User

interface CompanyDAO {
    //company operations
    fun createCompany(company: Company):String?
    fun getCompanies():Iterable<Company>
    fun getCompanyByName(companyName:String):Company?
    fun deleteCompany(companyName: String)
    fun editCompany(oldCompanyName: String, newCompany:Company):String?

    //department operations
    fun getDepartmentsFromCompany(companyName: String):Iterable<Department>
    fun getDepartmentByNameAndCompany(departmentName: String, companyName: String):Department?
    fun addDepartment(department: Department):String?
    fun deleteDepartmentFromCompany(departmentName: String, companyName: String)
    fun editDepartment(oldDepartmentName:String, newDepartment: Department, companyName: String):String?

    //user operation
    fun getAllUsersFromCompany(companyName: String):Iterable<User>
    //fun getUsersFromCompanyDepartment()
    fun addUser(newUser: User):String?
    fun deleteUser(login:String)
    fun editUser(oldUserLogin: String, newUser: User, companyName: String): String?

    //supplier operations
    fun getAllSuppliersFromCompany(companyName: String):Iterable<Supplier>
    //fun getSuppliersFromCompanyDepartment()
    fun addSupplier(newSupplier:Supplier):String?
    fun deleteSupplier(email:String)
    fun editSupplier(oldSupplierEmail: String, newSupplier: Supplier,companyName: String): String?

    //order operations
    fun getAllOrdersFromCompanyDepartment(departmentName: String, companyName: String):Iterable<Order>
    fun getOrderByName(orderName:String): Order?
}