package com.ClinicBackend.demo.Entities.ManageLoadedData

import jakarta.persistence.Entity
import lombok.Data

@Data
enum class DocType {
    ActualData,
    WriteOffData,
    AdmissionData
}