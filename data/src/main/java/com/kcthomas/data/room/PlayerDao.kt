package com.kcthomas.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kcthomas.domain.player.PLAYER_TABLE_NAME
import com.kcthomas.domain.player.Player
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface PlayerDao {

    @Query("SELECT * FROM $PLAYER_TABLE_NAME")
    fun getAll(): Single<List<Player>>

    @Query("SELECT * FROM $PLAYER_TABLE_NAME WHERE name = :name LIMIT 1")
    fun getPlayerByName(name: String): Maybe<Player>

    // Update an existing Row, or create a new one if it doesn't exist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: Player): Completable

    @Delete
    fun delete(player: Player): Completable

}
