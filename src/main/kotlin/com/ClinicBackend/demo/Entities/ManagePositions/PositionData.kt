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
    open var positionsDataId: Long? = null

    @Column(name = "position_name", length = 40)
    open var name: String? = null

    @Column(name = "unique_marker")
    open var uniqueMarker: Boolean? = null

    @ManyToOne
    @JoinColumn(name = "loaded_data_id")
    open var loadedData: LoadedData? = null

//  @ManyToMany(/*cascade = [CascadeType.ALL], */mappedBy = "departments")
//  open var users= mutableSetOf<User>()

    @Column(name = "count")
    open var count: Long? = null

    @Column(name = "edited_marker")
    open var editedMarker: Boolean? = null

    @Column(name = "processed_marker")
    open var processedMarker: Boolean? = null

    @ManyToOne
    @JoinColumn(name = "edited_by_id")
    open var editedBy: User? = null

    @ManyToOne
    @JoinColumn(name = "processed_by_id")
    open var processedBy: User? = null

    @OneToMany(mappedBy = "positionToPositionsData")
    open var positionAttributes= mutableSetOf<PositionAttributes>()

    @OneToMany(mappedBy = "positionToPositionsData")
    open var limits= mutableSetOf<Limits>()
}
