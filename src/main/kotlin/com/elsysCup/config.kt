package com.elsysCup

import io.ktor.server.config.*

data class DatabaseConfig(val applicationConfig: ApplicationConfig) {
    lateinit var jdbc: String
    lateinit var driver: String
    lateinit var user: String
    lateinit var password: String

    init {
        readConfig(applicationConfig)
    }

    private fun readConfig(applicationConfig: ApplicationConfig){
        jdbc = applicationConfig.property("ktor.database.jdbc").getString()
        driver = applicationConfig.property("ktor.database.driver").getString()
        user = applicationConfig.property("ktor.database.user").getString()
        password = applicationConfig.property("ktor.database.password").getString()
    }
}

data class CookieConfig(val applicationConfig: ApplicationConfig) {
    lateinit var lifetimeMin: String
    lateinit var secretEncryptKey: String
    lateinit var secretSignKey: String

    init {
        readConfig(applicationConfig)
    }

    private fun readConfig(applicationConfig: ApplicationConfig){
        lifetimeMin = applicationConfig.property("ktor.cookie.lifetimeMin").getString()
        secretEncryptKey = applicationConfig.property("ktor.cookie.secretEncryptKey").getString()
        secretSignKey = applicationConfig.property("ktor.cookie.secretSignKey").getString()
    }
}

data class JWT_Config(val applicationConfig: ApplicationConfig) {
    lateinit var secret: String
    lateinit var issuer: String
    lateinit var audience: String
    lateinit var myRealm : String

    init {
        secret = applicationConfig.property("ktor.jwt.secret").getString()
        issuer = applicationConfig.property("ktor.jwt.issuer").getString()
        audience = applicationConfig.property("ktor.jwt.audience").getString()
        myRealm = applicationConfig.property("ktor.jwt.realm").getString()
    }
}