package com.ClinicBackend.demo.Entities.ManagePositions

import com.ClinicBackend.demo.Entities.ManageLoadedData.LoadedData
import com.ClinicBackend.demo.Entities.ManageUsers.User
import jakarta.persistence.*

@Entity
@Table(name="positions_data")
open class PositionData() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_data_id")
    open var positionDataId: Long? = null

    @Column(name = "position_name", length = 40)
    open var name: String? = null

    @Column(name = "unique_marker")
    open var uniqueMarker: Boolean=false

    @ManyToOne
    @JoinColumn(name = "loaded_data_id")
    open var loadedData: LoadedData? = null

//  @ManyToMany(/*cascade = [CascadeType.ALL], */mappedBy = "departments")
//  open var users= mutableSetOf<User>()

    @Column(name = "count")
    open var count: Long? = null

    @Column(name = "edited_marker")
    open var editedMarker: Boolean=false

    @Column(name = "processed_marker")
    open var processedMarker: Boolean=false

    @ManyToOne
    @JoinColumn(name = "edited_by_id")
    open var editedBy: User? = null

    @ManyToOne
    @JoinColumn(name = "processed_by_id")
    open var processedBy: User? = null

    @OneToMany(mappedBy = "positionToPositionData", cascade = [CascadeType.ALL])
    open var attributes= mutableSetOf<PositionAttribute>()

    @OneToMany(mappedBy = "positionToPositionData", cascade = [CascadeType.ALL])
    open var limits= mutableSetOf<Limits>()
    override fun toString(): String {
        return "PositionData(\n" +
                "name=$name,\n" +
                "uniqueMarker=$uniqueMarker,\n" +
                "count=$count,\n" +
                "editedMarker=$editedMarker,\n" +
                "processedMarker=$processedMarker,\n" +
                "positionAttributes=[${attributes.joinToString("\n")}],\n" +
                "limits=[${limits.joinToString("\n")}])"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PositionData) return false

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
