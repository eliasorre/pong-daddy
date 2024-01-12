package com.elsysCup.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val email = varchar("email", 50).uniqueIndex()
    val nickName = varchar("nickName", 20)
    val name = varchar("name", 50)
    val lost = integer("lost")
    val won = integer("won")
    val pointDifference = integer("pd")
}

class UserInstance(entryId: EntityID<Int>) : IntEntity(entryId) {
    companion object : IntEntityClass<UserInstance>(Users)
    var email by Users.email
    var name by Users.name
    var nickName by Users.nickName
    var lost by Users.lost
    var won by Users.won
    var pointDifference by Users.pointDifference
}

@Serializable
data class User(
    val email : String,
    val nickName : String,
    val name : String,
    val lost : Int = 0,
    val won : Int = 0,
    val pointDifference : Int = 0,
    val skillLevelOwnEvaluation : Int,
    val skillLevelCalculated : Int = 0
)