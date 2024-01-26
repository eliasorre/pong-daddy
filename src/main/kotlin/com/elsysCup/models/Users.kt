package com.elsysCup.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

object Users : IntIdTable() {
    val email = varchar("email", 50).uniqueIndex()
    val nickName = varchar("nickName", 20)
    val name = varchar("name", 50)
    val image = blob("image").nullable()
    val lost = integer("lost")
    val won = integer("won")
    val pointDifference = integer("pd")
    val skillLevelOwnEvaluation = integer("skillOwnEval")
    val skillLevelCalculated = integer("skillCalc")
}

class UserInstance(entryId: EntityID<Int>) : IntEntity(entryId) {
    companion object : IntEntityClass<UserInstance>(Users)
    var email by Users.email
    var name by Users.name
    var nickName by Users.nickName
    var image by Users.image
    var lost by Users.lost
    var won by Users.won
    var pointDifference by Users.pointDifference
    var skillLevelOwnEvaluation by Users.skillLevelOwnEvaluation
    var skillLevelCalculated by Users.skillLevelCalculated
}


@Serializable
data class User(
    val email : String,
    val nickName : String,
    val name : String,
    @Contextual
    val image : ExposedBlob? = ExposedBlob(byteArrayOf()),
    val lost : Int = 0,
    val won : Int = 0,
    val pointDifference : Int = 0,
    val skillLevelOwnEvaluation : Int = 0,
    val skillLevelCalculated : Int = 0
)