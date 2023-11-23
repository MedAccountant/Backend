package com.ClinicBackend.demo.Entities.ManageLoadedData

import jakarta.persistence.Entity

//@Entity
enum class DocType {
    ActualData,
    WriteOffData,
    AdmissionData
}