package com.ClinicBackend.demo.Entities.ManageUsers

import jakarta.persistence.Entity

//@Entity
enum class Role{
    Admin,
    DataProducer,
    Editor
}