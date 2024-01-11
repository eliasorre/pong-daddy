package com.elsysCup.plugins

import com.elsysCup.db.UserDB
import com.elsysCup.models.User
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.userRoutes(userDB: UserDB) {
    authenticate("auth-jwt") {
        route("/user") {
            post {
                val newUser = call.receive<User>()
                userDB.createUser(newUser)
            }
        }
    }
}
