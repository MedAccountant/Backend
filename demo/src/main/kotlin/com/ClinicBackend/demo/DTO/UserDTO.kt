package com.ClinicBackend.demo.DTO

import com.ClinicBackend.demo.Entities.Roles
import com.ClinicBackend.demo.Entities.User

class UserDTO() {
    var login: String? = null
    var nickname: String? = null
    var role: Roles?=null
    var departments:List<DepartmentDTO>?=null

    constructor(user: User):this(){
        login=user.login
        nickname=user.nickname
        role=user.role
        departments=user.departments.map{ DepartmentDTO(it) }
    }
    /*fun makeUserFromDTO(): User {
        var newUser = User()
        newUser.login=login
        newUser.nickname=nickname
        newUser.role=role
        return newUser
    }*/

    override fun toString():String{
        var departmentsString=""
        departments!!.forEach {departmentsString+=it.toString() +",\n"}
        return  "{\n" +
                "\"login\":$login \n" +
                "\"nickname\":$nickname \n" +
                "\"role\":$role \n" +
                "\"departments\":[\n" +
                " \n" +
                "}"
    }
}