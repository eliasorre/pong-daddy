package com.elsysCup.models

import kotlinx.serialization.Serializable

@Serializable
data class ScoreBoardRow (
    val nickName : String,
    val position : Int,
    val won : Int,
    val lost : Int,
    val pointDiference : Int,
    val form : String
)

@Serializable
data class ScoreBoard(
    val rows : List<ScoreBoardRow>
)