package com.ClinicBackend.demo.ManageFiles.DBWork

open class SavePositionException: RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}