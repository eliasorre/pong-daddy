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


            // check if user already exists.
            post {
                val newUser = call.receive<User>()
                if(!userDB.userExists(newUser)){
                    if(userDB.createUser(newUser)){
                        call.respond(HttpStatusCode.OK)
                    }else{
                        call.respond(HttpStatusCode.ExpectationFailed, "Unable to create user")
                    }
                } else {
                    call.respond(HttpStatusCode.Conflict, "User with this email already exists")
                }
            }

            put {
                // Get the current user version
                val principal = call.principal<JWTPrincipal>()
                val userEmail = principal!!.payload.getClaim("userEmail").asString()
                val user      = userDB.getUserByEmail(userEmail)

                //Get the updated user
                val updatedUser = call.receive<User>()

                if(user != null){
                    if(userDB.updateUser(user, updatedUser)){
                        call.respond(HttpStatusCode.OK)
                    }else{
                        call.respond(HttpStatusCode.NotModified, "Unable to update user")
                    }
                }else{
                    call.respond(HttpStatusCode.NotFound, "User with this email does not exist")
                }
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userEmail = principal!!.payload.getClaim("userEmail").asString()
                val user      = userDB.getUserByEmail(userEmail)

                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.NotFound, "User with this email does not exist")
                }

            }
        }
    }
}
