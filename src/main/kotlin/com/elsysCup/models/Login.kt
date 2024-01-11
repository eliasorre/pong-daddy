package com.elsysCup.models

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.elsysCup.JWT_Config
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.util.*

object LoginTable : IntIdTable() {
    val email = varchar("email", 50).uniqueIndex()
    val password = varchar("passwd", 50)
}

class LoginInstance(entryId: EntityID<Int>) : IntEntity(entryId) {
    companion object : IntEntityClass<LoginInstance>(LoginTable)
    var email by LoginTable.email
    var password by LoginTable.password
}

@Serializable
data class Login(
    val email : String,
    val password : String
)

fun createValidToken(jwtConfig : JWT_Config, userEmail : String) : String? {
        return JWT.create()
            .withAudience(jwtConfig.audience)
            .withIssuer(jwtConfig.issuer)
            .withClaim("userEmail", userEmail)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(jwtConfig.secret))
}
