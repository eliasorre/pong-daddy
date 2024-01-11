package com.elsysCup.db

import com.elsysCup.models.User
import com.elsysCup.models.UserInstance
import com.elsysCup.models.Users
import org.jetbrains.exposed.sql.insert

class UserDB (val database: MyDatabase) {
    suspend fun createUser(user : User) {
        val existingUsers = database.dbQuery { UserInstance.find { Users.email eq user.email }.count() }
        if (existingUsers.toInt() != 0) throw Exception("User exist")

        database.dbQuery { Users.insert {
            it[nickName] = user.nickName
            it[email] = user.email
            it[name] = user.name
            it[won] = 0
            it[lost] = 0
            it[pointDifference] = 0 }
        }
    }
}