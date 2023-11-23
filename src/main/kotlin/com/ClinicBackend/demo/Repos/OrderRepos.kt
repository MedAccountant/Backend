package com.ClinicBackend.demo.Repos

import com.ClinicBackend.demo.Entities.ManageOrders.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepos: JpaRepository<Order, Long> {
    fun findByLinkToFileEndsWith(fileName:String): Order?
}