package com.kcthomas.data.match

import com.kcthomas.domain.match.Match
import com.kcthomas.domain.match.MatchRepository
import io.reactivex.Single

class MatchRepositoryImpl(
    private val cachedSource: CachedMatchSource
    // TODO private val remoteSource: RemoteMatchSource
) : MatchRepository {

    // TODO - In a real app, the Repository would decide
    // TODO - where to get the data from (disk vs over the network)
    override fun getAll(): Single<List<Match>> {
        val shouldUseCache = true // Will be some criteria in reality
        return if (shouldUseCache) {
            cachedSource.getAll()
        } else {
            // remoteSource.getAll()
            TODO()
        }
    }

    override fun insert(match: Match) = cachedSource.insert(match)

    override fun delete(match: Match) = cachedSource.delete(match)

}
