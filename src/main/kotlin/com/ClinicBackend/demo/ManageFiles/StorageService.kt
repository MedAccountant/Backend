package com.ClinicBackend.demo.ManageFiles

import com.ClinicBackend.demo.Entities.ManageLoadedData.DocType
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path


interface StorageService {

    //fun init()
    fun store(file: MultipartFile?):String
    fun loadAll(): Set<Path?>?
    fun load(filename: String?): Path?
    //fun loadAsResource(filename: String?): Resource?
    fun deleteAll()

    fun inventoryProcessingWithLoadedFile(path:Path){

    }
}