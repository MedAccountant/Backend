package com.ClinicBackend.demo.Security

import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
class AuthResponse() {
    var jwtToken:String?=null
    constructor(token:String):this(){jwtToken=token}
}