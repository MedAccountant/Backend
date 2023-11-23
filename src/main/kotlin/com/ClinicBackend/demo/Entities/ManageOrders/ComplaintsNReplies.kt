package com.ClinicBackend.demo.Entities.ManageOrders

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManageUsers.Role
import com.ClinicBackend.demo.Entities.ManageUsers.User
import jakarta.persistence.*

@Entity
@Table(name="complaints_and_replies")
open class ComplaintsNReplies() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    open var crId:Long?=null

    @Column(name="theme",length = 50)
    open var theme:String?=null

    @Column(name="complaint",length = 255)
    open var complaint:String?=null

    @Column(name="reply",length = 255)
    open var reply:String?=null

    /*@ManyToOne !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Join*/@Column(name = "recipient_role")
    open var recipientRole: Role?=null

    @ManyToOne
    @JoinColumn(name = "sender_department_id")
    open var senderDepartment: Department?=null

    @Column(name="read_marker")
    open var readMarker:Boolean?=null

    @Column(name="closed_marker")
    open var closedMarker:Boolean?=null

    @ManyToOne
    @JoinColumn(name = "sender_id")
    open var sender: User?=null

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    open var recipient: User?=null

}