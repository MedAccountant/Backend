package com.ClinicBackend.demo.Entities

import jakarta.persistence.*

@Entity
@Table(name="departments")
open class Department() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    open var departmentId:Long?=null

    @Column(name="department_name",length = 40)
    open var departmentName:String?=null

    @Column(name="working_marker")
    open var workingMarker:Boolean?=null

    @ManyToOne
    @JoinColumn(name = "company_id")
    open var company:Company?=null

    @ManyToMany(/*cascade = [CascadeType.ALL], */mappedBy = "departments")
    open var users= mutableSetOf<User>()

    fun deleteUser(user: User)=users.remove(user)

    fun editDepartment(newDepartment: Department){
        departmentName=newDepartment.departmentName
        workingMarker=newDepartment.workingMarker
    }

    override fun toString():String{
        var usersString=""
        users!!.forEach {usersString+="{\n"+it.nickname +"},\n"}
        return  "{\n" +
                "\"departmentName\":$departmentName \n" +
                "\"users\":[\n" +
                usersString+
                "]\n" +
                "}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Department) return false

        if (departmentName != other.departmentName) return false

        return true
    }

    override fun hashCode(): Int {
        return departmentName?.hashCode() ?: 0
    }


}