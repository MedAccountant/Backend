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

    @Column(name="doc_type")
    @Enumerated(EnumType.STRING)
    open var documentType: DocType?=null

    @OneToMany(mappedBy = "loadedData", cascade = [CascadeType.ALL])
    open var positions=mutableSetOf<PositionData>()

    override fun toString()="{\n" +
                "\"loadID\":$loadId \n" +
                "\"linkToFile\":$linkToFile \n" +
                "\"department\":$department \n" +
                "\"sender\":$sender \n" +
                "\"departmentType\":$documentType \n" +
                "}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LoadedData) return false

        if (linkToFile != other.linkToFile) return false

        return true
    }

    override fun hashCode(): Int {
        return linkToFile?.hashCode() ?: 0
    }

}