package com.ClinicBackend.demo.Entities.ManageLoadedData

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManagePositions.PositionData
import com.ClinicBackend.demo.Entities.ManageUsers.User
import jakarta.persistence.*

@Entity
@Table(name="loaded_data")
open class LoadedData() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "load_id")
    open var loadId:Long?=null

    @Column(length = 255, unique = true)
    open var linkToFile: String? = null

    @ManyToOne
    @JoinColumn(name = "department_id")
    open var department: Department?=null

    @ManyToOne
    @JoinColumn(name = "sender_id")
    open var sender: User?=null

    /*@ManyToOne !!!!!!!!!!!!
    @JoinColumn(name = "document_type")
    @Enumerated(EnumType.STRING)*/
    @Column(name="doc_type")
    open var documentType: DocType?=null

    @OneToMany(mappedBy = "loadedData")
    open var positions=mutableSetOf<PositionData>()

    override fun toString()="{\n" +
                "\"loadID\":$loadId \n" +
                "\"linkToFile\":$linkToFile \n" +
                "\"department\":$department \n" +
                "\"sender\":$sender \n" +
                "\"departmentType\":$documentType \n" +
                "}"
}