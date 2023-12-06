package com.ClinicBackend.demo.Entities.ManagePositions

import com.ClinicBackend.demo.Entities.Company
import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.ManageFiles.DBWork.CurrentPositionsListener
import com.ClinicBackend.demo.ManageFiles.DBWork.CurrentPositionsService
import jakarta.persistence.*
import org.apache.catalina.core.ApplicationContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.Transient

//@EntityListeners(CurrentPositionsListener::class)
@Entity
@Table(name="current_positions")
open class CurrentPosition() {

    constructor(positionData: PositionData, department_: Department):this(){
        name=positionData.name

        count=positionData.count

        attributes.addAll(positionData.attributes.map {attribute->
            PositionAttribute(attribute).also {
                it.positionToPositionData=null
                it.positionToCurrentPosition=this }
             }
        )

        limits.addAll(positionData.limits.map {limits-> Limits(limits,this)})

        department=department_
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "current_position_id")
    open var currentPositionId: Long? = null

    @Column(name = "position_name", unique = true, length = 60)
    open var name: String? = null

    @Column(name = "count")
    open var count: Long? = null

    @OneToMany(mappedBy = "positionToCurrentPosition", cascade=[CascadeType.ALL])
    open var attributes= mutableSetOf<PositionAttribute>()

    @OneToMany(mappedBy = "positionToCurrentPosition", cascade=[CascadeType.ALL])
    open var limits= mutableSetOf<Limits>()

    @ManyToOne
    @JoinColumn(name = "department_id")
    open var department: Department?=null

    override fun toString(): String {
        return "CurrentPosition(\n" +
                "name=$name,\n" +
                "count=$count,\n" +
                "department=${department!!.departmentName},\n" +
                "positionAttributes=[${attributes.joinToString("\n")}],\n" +
                "limits=[${limits.joinToString("\n")}])"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CurrentPosition) return false

        if (name != other.name) return false
        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + attributes.hashCode()
        return result
    }
}