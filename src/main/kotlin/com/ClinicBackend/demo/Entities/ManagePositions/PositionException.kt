package com.ClinicBackend.demo.Entities.ManagePositions

open class PositionException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}