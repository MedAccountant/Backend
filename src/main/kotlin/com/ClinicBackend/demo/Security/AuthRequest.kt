package com.ClinicBackend.demo.Security

import lombok.Data

@Data
class AuthRequest() {
    lateinit var login:String
    lateinit var password:String
}