package com.kcthomas.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kcthomas.domain.match.MATCH_TABLE_NAME
import com.kcthomas.domain.match.Match
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MatchDao {

    @Query("SELECT * FROM $MATCH_TABLE_NAME")
    fun getAll(): Single<List<Match>>

    // Update an existing Row, or create a new one if it doesn't exist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(match: Match): Completable

    @Delete
    fun delete(match: Match): Completable

}
