package com.elsysCup

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.elsysCup.db.MyDatabase
import com.elsysCup.plugins.authRoutes
import com.elsysCup.plugins.userRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    val jwt_Config = JWT_Config(environment.config)
    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwt_Config.myRealm
            verifier(
                JWT
                .require(Algorithm.HMAC256(jwt_Config.secret))
                .withAudience(jwt_Config.audience)
                .withIssuer(jwt_Config.issuer)
                .build())
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    val config = DatabaseConfig(environment.config)
    val myDatabase = MyDatabase(config)

    install(Routing) {
        authRoutes(myDatabase.loginDB, jwt_Config)
        userRoutes(myDatabase.userDB)
    }
}
