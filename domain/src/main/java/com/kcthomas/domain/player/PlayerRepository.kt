package com.kcthomas.domain.player

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface PlayerRepository {

    fun getAll(): Single<List<Player>>

    fun getPlayerByName(name: String): Maybe<Player>

    fun insert(player: Player): Completable

    fun delete(player: Player): Completable

}
