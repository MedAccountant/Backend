package com.ClinicBackend.demo.Entities

import jakarta.persistence.*


@Entity
@Table(name="suppliers")
open class Supplier() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "supplier_id")
    open var supplierId:Long?=null

    @Column(length = 40, unique = true)
    open var email: String? = null

    @Column(nullable = false, length = 40)
    open var name: String? = null

    @ManyToMany
    @JoinTable(name="supplier_department",
        joinColumns=[JoinColumn (name="supplier_id")],
        inverseJoinColumns=[JoinColumn(name="department_id")])
    open var departments= mutableSetOf<Department>()

    @Column(name="working_marker")
    open var workingMarker:Boolean?=null

    fun editSupplier(newSupplier: Supplier){
        email=newSupplier.email
        name=newSupplier.name
        departments=newSupplier.departments
        workingMarker=newSupplier.workingMarker
    }

    override fun toString():String{
        var departmentsString=""
        departments!!.forEach {departmentsString+=it.toString() +",\n"}
        return  "{\n" +
                "\"name\":$name \n" +
                "\"email\":$email \n" +
                "\"workingMarker\":$workingMarker \n" +
                "\"departments\":[\n" +
                departmentsString+
                "\n]\n" +
                "}"
    }
}