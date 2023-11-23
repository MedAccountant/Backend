package com.ClinicBackend.demo.Entities.ManageOrders

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageUsers.User
import com.ClinicBackend.demo.Entities.Supplier
import jakarta.persistence.*

@Entity
@Table(name="orders")
open class Order() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    open var orderId:Long?=null

    @Column(name="link_to_file",length = 40, unique = true)
    open var linkToFile:String?=null

    //!!!!!
    @ManyToOne
    @JoinColumn(name = "department_id")
    open var department: Department?=null

    @Column(name="read_marker_dp")
    open var readMarkerDP:Boolean?=null

    @Column(name="read_marker_editor")
    open var readMarkerEditor:Boolean?=null

    @Column(name="edited_marker")
    open var editedMarker:Boolean?=null

    @Column(name="verified_marker")
    open var verifiedMarker:Boolean?=null

    @Column(name="bad_order_marker")
    open var badOrderMarker:Boolean?=null

    @Column(name="done_order_marker")
    open var doneOrderMarker:Boolean?=null

    @Column(name="description")
    open var description:String?=null

    @Column(name="complaintDescription")
    open var complaintDescription:String?=null

    @ManyToOne
    @JoinColumn(name = "sender_id")
    open var sender: User?=null

    @ManyToOne
    @JoinColumn(name = "sent_to_id")
    open var sentTo: Supplier?=null

    @ManyToOne
    @JoinColumn(name = "edited_by_id")
    open var editedBy: User?=null

    @ManyToOne
    @JoinColumn(name = "complaint_by_id")
    open var complaintBy: User?=null

    @ManyToOne
    @JoinColumn(name = "done_by_id")
    open var doneBy: User?=null

}