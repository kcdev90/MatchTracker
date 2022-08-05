package com.kcthomas.data.player

import com.kcthomas.data.room.AppDatabase
import com.kcthomas.domain.player.Player
import com.kcthomas.domain.player.PlayerRepository

class CachedPlayerSource(appDatabase: AppDatabase) : PlayerRepository {

    private val playerDao = appDatabase.playerDao()

    override fun getAll() = playerDao.getAll()

    override fun getPlayerByName(name: String) = playerDao.getPlayerByName(name)

    override fun insert(player: Player) = playerDao.insert(player)

    override fun delete(player: Player) = playerDao.delete(player)

}
