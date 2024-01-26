package com.elsysCup.db

import com.elsysCup.models.*
import org.jetbrains.exposed.sql.insert

class LoginDB (val database: MyDatabase) {
    suspend fun checkLogin(loginInfo : Login?): Boolean {
        if (loginInfo == null) throw Exception("Invalid login")
        return database.dbQuery { LoginInstance.find { LoginTable.email eq loginInfo.email }
            .limit(1)
            .all { it.password == loginInfo.password  }}
    }

    suspend fun createLogin(loginInfo : Login) {
        val existingUsers = database.dbQuery { UserInstance.find { Users.email eq loginInfo.email }.count() }
        if (existingUsers.toInt() != 0) throw Exception("User exist")
        database.dbQuery { LoginTable.insert {
            it[email] = loginInfo.email
            it[password] = loginInfo.password }
        }
    }
}