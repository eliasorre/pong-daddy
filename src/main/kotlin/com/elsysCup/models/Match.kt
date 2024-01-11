package com.elsysCup.models;

import kotlinx.serialization.Serializable

@Serializable
data class SingleMatch(
    val matchID : String,
    val homePlayer : String,
    val awayPlayer : String,
    val deadLine : Long,
    val homePlayerScore : Int,
    val awayPlayerScore : Int,
    val played : Boolean,
    val homePlayerConfirmed : Boolean,
    val awayPlayerConfirmed : Boolean
)

@Serializable
data class Matches(
    val matches : List<SingleMatch>
)

@Serializable
data class RegisterScore(
    val userRegistering : String,
    val homePlayerScore : Int,
    val awayPlayerScore : Int,
)