package com.elsysCup.plugins

import com.elsysCup.JWT_Config
import com.elsysCup.db.LoginDB
import com.elsysCup.models.Login
import com.elsysCup.models.createValidToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(loginDB: LoginDB, jwtConfig: JWT_Config) {
    post("/login") {
        val login = call.receive<Login>()
        if (!loginDB.checkLogin(login)) call.respond(HttpStatusCode.Unauthorized, "Invalid login")
        val token = createValidToken(jwtConfig, login.email)
        call.respond(hashMapOf("token" to token))
    }

    post("/register") {
        val login = call.receive<Login>()
        loginDB.createLogin(login)
        val token = createValidToken(jwtConfig, login.email)
        call.respond(hashMapOf("token" to token))
    }
}
