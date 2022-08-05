package com.kcthomas.domain.match

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val MATCH_TABLE_NAME = "matches"

@Entity(tableName = MATCH_TABLE_NAME)
data class Match(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "player_1_name") val player1Name: String,
    @ColumnInfo(name = "player_1_score") val player1Score: Int,
    @ColumnInfo(name = "player_2_name") val player2Name: String,
    @ColumnInfo(name = "player_2_score") val player2Score: Int,
    val timestamp: String
)
