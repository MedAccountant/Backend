package com.ClinicBackend.demo.ManageFiles

import com.ClinicBackend.demo.Entities.ManageLoadedData.DocType
import com.ClinicBackend.demo.ManageFiles.Exceptions.StorageException
import com.ClinicBackend.demo.Repos.LoadedDataRepos
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.pathString


@Service
class FileSystemStorageService (@Autowired properties: StorageProperties) : StorageService {
    private val rootLocation: Path
    private val rootLocationWright:Path


    init {
        if (properties.location.trim { it <= ' ' }.length == 0 ||
            properties.wright.trim { it <= ' ' }.length == 0) {
            throw StorageException("File upload location can not be Empty.")
        }
        rootLocation = Paths.get(properties.location)
        rootLocationWright=Paths.get(properties.wright)
        try {
            Files.createDirectories(rootLocation)
            Files.createDirectories(rootLocationWright)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    fun saveXLSasCSV(source:Path, destination:Path){
        println("xls to csv")
        /*// Load the input Excel file
        val workbook = Workbook(source.pathString)
        // Save output CSV file
        workbook.save(destination.pathString, SaveFormat.CSV)*/

        // Open and existing XLSX file
        val fileInStream = FileInputStream(source.pathString)
        val fileOutStream=FileOutputStream(destination.pathString)
        val writer=fileOutStream.bufferedWriter()
        val workBook = XSSFWorkbook(fileInStream)
        val sheet = workBook.getSheetAt(0)
        val column_count=sheet.getRow(0).lastCellNum+1
        // Loop through all the rows
        val rowIterator = sheet.iterator()
        while (rowIterator.hasNext()) {
            val row: Row = rowIterator.next()
            // Loop through all rows and add ","
            val cellIterator = row.cellIterator()
            val stringBuffer = StringBuffer()
            for (cn in 0 until column_count) {
                val cell: Cell? = row.getCell(cn)
                if (stringBuffer.isNotEmpty()) {
                    stringBuffer.append("|")
                }
                stringBuffer.append(
                    if(cell!=null) when (cell.cellType) {
                        CellType.BOOLEAN -> "${cell.booleanCellValue}"
                        CellType.NUMERIC -> "${cell.numericCellValue}"
                        CellType.STRING -> cell.stringCellValue
                        else -> ("${cell.cellType}")
                    }
                else (" ").also { println("blank")})
            }
            writer.appendLine(stringBuffer.toString())
        }
        writer.flush()
        fileInStream.close()
        fileOutStream.close()
        workBook.close()
    }

    override fun store(file: MultipartFile?):String {
        try {
            if (file!!.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }
            val destinationFile: Path = rootLocation.resolve(
                Paths.get(file.originalFilename!!)
            ).normalize().toAbsolutePath()
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                // This is a security check
                throw StorageException(
                    "Cannot store file outside current directory."
                )
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
            println("destination: ${destinationFile.pathString}")
            if(destinationFile.pathString.endsWith(".xlsx"))saveXLSasCSV(destinationFile, rootLocationWright.resolve(
                    Paths.get(file.originalFilename!!.replace(".xlsx",".csv"))
                    ).normalize().toAbsolutePath())
            return rootLocationWright.resolve(Paths.get(file.originalFilename!!.replace(".xlsx",".csv"))).pathString
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
    }

    override fun loadAll(): Set<Path?>? {
        return try {
            Files.walk(rootLocation, 1).toList()
                .filter { path: Path -> path != rootLocation }
                .map(rootLocation::relativize).toSet()
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String?): Path? {
        return rootLocation.resolve(filename!!)//!!!!!!!exceptions
    }

    /*override fun loadAsResource(filename: String): Resource {
        return try {
            val file: Path? = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable()) {
                resource
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }*/

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
        FileSystemUtils.deleteRecursively(rootLocationWright.toFile())
    }


}