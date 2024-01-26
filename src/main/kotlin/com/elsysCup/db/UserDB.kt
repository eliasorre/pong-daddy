package com.elsysCup.db

import com.elsysCup.models.User
import com.elsysCup.models.UserInstance
import com.elsysCup.models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.update

class UserDB (val database: MyDatabase) {
    suspend fun createUser(user: User): Boolean {
        try{
            database.dbQuery {
                Users.insert {
                    it[nickName] = user.nickName
                    it[email] = user.email
                    it[name] = user.name
                    it[image] = user.image
                    it[won] = 0
                    it[lost] = 0
                    it[pointDifference] = 0
                    it[skillLevelOwnEvaluation] = user.skillLevelOwnEvaluation
                    it[skillLevelCalculated] = 0
                }
            }
        } catch(e: Exception) {
            return false
        }
        return true

    }

    suspend fun userExists(user: User): Boolean {
        val existingUser = database.dbQuery { UserInstance.find { Users.email eq user.email }.count() }
        return (existingUser.toInt() != 0)
    }


    suspend fun updateUser(user: User, updatedUser : User): Boolean {
        try{
            database.dbQuery {
                Users.update({ Users.email eq user.email }) {
                    it[email]  = updatedUser.email
                    it[nickName] = updatedUser.nickName
                    it[name] = updatedUser.name
                    it[image] = updatedUser.image
                    it[won] = updatedUser.won
                    it[lost] = updatedUser.lost
                    it[pointDifference] = updatedUser.pointDifference
                    it[skillLevelOwnEvaluation] = updatedUser.skillLevelOwnEvaluation
                    it[skillLevelCalculated] = updatedUser.skillLevelCalculated
                }
            }
        } catch (e: Exception){
            return false
        }
        return true

    }

    suspend fun getUserByEmail(email: String): User? {
        return database.dbQuery {
            Users.select(Users.email eq email)
                .map { row ->
                    User(
                        email = row[Users.email],
                        nickName = row[Users.nickName],
                        name = row[Users.name],
                        image = row[Users.image],
                        lost = row[Users.lost],
                        won = row[Users.won],
                        pointDifference = row[Users.pointDifference],
                        skillLevelOwnEvaluation = row[Users.skillLevelOwnEvaluation],
                        skillLevelCalculated = row[Users.skillLevelCalculated]
                    )
                }
                .singleOrNull()
        }
    }

}