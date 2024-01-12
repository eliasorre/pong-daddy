package com.elsysCup.plugins

import com.elsysCup.db.UserDB
import com.elsysCup.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userDB: UserDB) {
    authenticate("auth-jwt") {
        route("/user") {
            // TODO: check if user already exists
            post {
                val newUser = call.receive<User>()
                userDB.createUser(newUser)
                call.respond(HttpStatusCode.OK)
            }
            // TODO: Implement update
            put {
                val existingUser = call.receive<User>()
            }
            // TODO: Implement
            get {
                val principal = call.principal<JWTPrincipal>()
                val userEmail = principal!!.payload.getClaim("userEmail").asString()
                // val user = User()
                // call.respond(user)
            }
        }
    }
}
