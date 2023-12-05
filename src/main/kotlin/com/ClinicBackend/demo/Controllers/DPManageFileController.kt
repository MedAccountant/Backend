package com.ClinicBackend.demo.Controllers


import com.ClinicBackend.demo.Entities.ManageLoadedData.DocType
import com.ClinicBackend.demo.ManageFiles.DBWork.LoadedDataDAO
import com.ClinicBackend.demo.ManageFiles.DBWork.LoadedDataService
import com.ClinicBackend.demo.ManageFiles.Exceptions.StorageFileNotFoundException
import com.ClinicBackend.demo.ManageFiles.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/{companyName}/DataProducer/Files")
class DPManageFileController{
    @Autowired
    lateinit private var storageService: StorageService

    @Autowired
    lateinit private var loadedDataService: LoadedDataService

    @GetMapping()
    fun check()=ResponseEntity.ok("ok")

    /*@GetMapping("/")
    @Throws(IOException::class)
    fun listUploadedFiles(@RequestParam("login") DPLogin:String): ResponseEntity<List<Path>> {
        storageService.loadAll().map { path ->
            MvcUriComponentsBuilder.fromMethodName(
                FileUploadController::class.java,
                "serveFile", path.getFileName().toString()
            ).build().toUri().toString()
        return "uploadForm"
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    fun serveFile(@PathVariable filename: String?): ResponseEntity<Resource> {
        val file: Resource =
            storageService.loadAsResource(filename) ?: return ResponseEntity.notFound().build<Resource>()
        return ResponseEntity.ok().header(
            HttpHeaders.CONTENT_DISPOSITION,
            ("attachment; filename=\"" + file.getFilename()).toString() + "\""
        ).body<Resource>(file)
    }*/

    @PostMapping()
    fun handleFileUpload(
        @PathVariable companyName:String,
        @RequestParam("doc_type") docType:DocType,
        @RequestParam("file") file: MultipartFile
    ):ResponseEntity<String> {
        loadedDataService.processFile(
            storageService.store(file),
            docType,
            companyName)
        return ResponseEntity.ok(file.name)
    }

    @ExceptionHandler(StorageFileNotFoundException::class)
    fun handleStorageFileNotFound(exc: StorageFileNotFoundException?): ResponseEntity<*> {
        return ResponseEntity.notFound().build<Any>()
    }
}