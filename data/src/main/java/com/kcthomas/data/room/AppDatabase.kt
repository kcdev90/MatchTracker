package com.kcthomas.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kcthomas.domain.match.Match
import com.kcthomas.domain.player.Player

const val APP_DATABASE_NAME = "appdatabase.db"

@Database(
    entities = [Match::class, Player::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    abstract fun playerDao(): PlayerDao

}
