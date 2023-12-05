package com.ClinicBackend.demo.ManageFiles

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("storage")
class StorageProperties {
    var location = "uploads"
    var wright="wright_uploads"
}