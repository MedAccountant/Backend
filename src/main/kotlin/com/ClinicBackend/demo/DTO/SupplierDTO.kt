package com.ClinicBackend.demo.DTO

import com.ClinicBackend.demo.Entities.Supplier

class SupplierDTO() {
    var email: String? = null
    var name: String? = null
    var departments:List<DepartmentDTO>? = null
    var workingMarker:Boolean? = null

    constructor(supplier: Supplier):this(){
        email=supplier.email
        name=supplier.name
        workingMarker=supplier.workingMarker
        departments=supplier.departments.map{ DepartmentDTO(it) }
    }
    fun makeSupplierFromDTO(): Supplier {
        var newSupplier = Supplier()
        newSupplier.email=email
        newSupplier.name=name
        newSupplier.workingMarker=workingMarker
        return newSupplier
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