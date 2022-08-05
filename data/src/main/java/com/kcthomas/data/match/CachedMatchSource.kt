package com.kcthomas.data.match

import com.kcthomas.data.room.AppDatabase
import com.kcthomas.domain.match.Match
import com.kcthomas.domain.match.MatchRepository
import com.kcthomas.domain.player.Player
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import java.util.UUID

class CachedMatchSource(appDatabase: AppDatabase) : MatchRepository {

    private var matchDao = appDatabase.matchDao()
    private val playerDao = appDatabase.playerDao()

    override fun getAll() = matchDao.getAll()

    override fun insert(match: Match): Completable {
        // Not possible to observe a Room transaction with RxJava?
        // https://stackoverflow.com/questions/62091581/how-to-perform-a-room-transaction-usin-rxjava
        // appDatabase.runInTransaction {
        //
        // }


//        playerDao.insert(
//            Player(
//                id = "playerid",
//                name = "test",
//                matchesPlayed = "9",
//                matchesWon = "8"
//            )
//        )

//        insertPlayer(
//            playerName = match.player1Name,
//            playerWon = match.player1Score > match.player2Score
//        )
//
//        insertPlayer(
//            playerName = match.player2Name,
//            playerWon = match.player2Score > match.player1Score
//        )


        return matchDao.insert(match)

    }



    override fun delete(match: Match) = matchDao.delete(match)

}
