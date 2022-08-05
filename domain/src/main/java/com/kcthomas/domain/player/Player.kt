package com.kcthomas.domain.player

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val PLAYER_TABLE_NAME = "players"

@Entity(tableName = PLAYER_TABLE_NAME)
data class Player(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "matches_played") val matchesPlayed: Int,
    @ColumnInfo(name = "matches_won") val matchesWon: Int
)
