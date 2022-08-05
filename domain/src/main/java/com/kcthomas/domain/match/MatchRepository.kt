package com.kcthomas.domain.match

import io.reactivex.Completable
import io.reactivex.Single

interface MatchRepository {

    fun getAll(): Single<List<Match>>

    fun insert(match: Match): Completable

    fun delete(match: Match): Completable

}
