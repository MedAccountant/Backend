package com.ClinicBackend.demo.DTO.CreateDTOs

import com.ClinicBackend.demo.DTO.DepartmentDTO
import com.ClinicBackend.demo.Entities.ManageUsers.Role
import com.ClinicBackend.demo.Entities.ManageUsers.User

class UserCreationDTO() {
    var login: String? = null
    var nickname: String? = null
    var role: Role?=null
    var password:String?=null
    var departments:Set<DepartmentDTO>?=null

    /*constructor(user: User):this(){
        login=user.login
        nickname=user.nickname
        role=user.role
        departments=user.departments.map{ DepartmentDTO(it) }
    }*/
    fun makeUserFromDTO(): User {
        var newUser = User()
        newUser.login=login
        newUser.nickname=nickname
        newUser.role=role
        newUser.password=password
        return newUser
    }

    override fun toString():String{
        var departmentsString=""
        departments!!.forEach {departmentsString+=it.toString() +",\n"}
        return  "{\n" +
                "\"login\":$login \n" +
                "\"nickname\":$nickname \n" +
                "\"role\":$role \n" +
                "\"password\":$password \n" +
                "\"departments\":[\n" +
                " \n" +
                "}"
    }
}