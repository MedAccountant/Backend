package com.ClinicBackend.demo.Entities

import jakarta.persistence.*
import org.hibernate.Hibernate

enum class Roles{
    Admin,
    DataProducer,
    Editor
}

@Entity
@Table(name="users")
open class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    open var userId:Long?=null

    @Column(length = 40, unique = true)
    open var login: String? = null

    @Column(nullable = false, length = 40)
    open var nickname: String? = null

    @Column()
    open var role: Roles?=null

    @ManyToMany
    @JoinTable (name="user_department",
        joinColumns=[JoinColumn (name="user_id")],
        inverseJoinColumns=[JoinColumn(name="department_id")])
    open var departments= mutableSetOf<Department>()

    @Column(length = 30)
    open var password:String?=null

    fun editUser(newUser: User){
        //login=newUser.login
        nickname=newUser.nickname
        role=newUser.role
        departments=newUser.departments
        password=newUser.password
    }

    fun removeDepartment(department: Department) {
        departments.remove(department)
        println("removeDepartment, removed from departments")
        /*if(Hibernate.isInitialized(department.users)){
            department.users.remove(this)
            println("removeDepartment, removed from department.users")
        }*/
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
                departmentsString+
                "\n]\n" +
                "}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (login != other.login) return false

        return true
    }

    override fun hashCode(): Int {
        return login?.hashCode() ?: 0
    }
}