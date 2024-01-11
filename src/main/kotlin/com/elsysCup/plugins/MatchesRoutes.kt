package com.elsysCup.plugins

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.matchRoutes() {
    authenticate("auth-jwt") {
        route("/matches") {
            // TODO: return all matches for current user
            get("") {

            }
            // TODO: return a singleMatchObject
            get("?{matchId}") {

            }
            // TODO: return all unplayed matches for current user
            get("unplayed") {

            }
        }
    }
}
