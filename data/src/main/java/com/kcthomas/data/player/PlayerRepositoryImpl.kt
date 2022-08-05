package com.kcthomas.data.player

import com.kcthomas.domain.player.Player
import com.kcthomas.domain.player.PlayerRepository
import io.reactivex.Single

class PlayerRepositoryImpl(
    private val cachedSource: CachedPlayerSource
    // TODO private val remoteSource: RemotePlayerSource
) : PlayerRepository {

    // TODO - In a real app, the Repository would decide
    // TODO - where to get the data from (disk vs over the network)
    override fun getAll(): Single<List<Player>> {
        val shouldUseCache = true // Will be some criteria in reality
        return if (shouldUseCache) {
            cachedSource.getAll()
        } else {
            // remoteSource.getAll()
            TODO()
        }
    }

    override fun getPlayerByName(name: String) = cachedSource.getPlayerByName(name)

    override fun insert(player: Player) = cachedSource.insert(player)

    override fun delete(player: Player) = cachedSource.delete(player)

}
