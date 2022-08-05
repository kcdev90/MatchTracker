package com.kcthomas.matchtracker.ui.matchhistory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kcthomas.core.util.LoadState
import com.kcthomas.data.match.MatchRepositoryImpl
import com.kcthomas.data.player.PlayerRepositoryImpl
import com.kcthomas.domain.match.Match
import com.kcthomas.domain.player.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MatchHistoryViewModel @Inject constructor(
    //TODO repo interface
    private val matchRepository: MatchRepositoryImpl,
    private val playerRepository: PlayerRepositoryImpl
) : ViewModel() {

    companion object {
        val TAG = MatchHistoryViewModel::class.simpleName
    }

    private val _viewState = MutableLiveData<LoadState<List<Match>>>(LoadState.InFlight)
    val viewState
        get() = _viewState

    private val disposables = CompositeDisposable()

    init {
        loadMatches()
    }

    private fun loadMatches() {
        _viewState.value = LoadState.InFlight
        matchRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { matches ->
                    _viewState.value = LoadState.Success(matches)
                },
                { throwable ->
                    Log.e(TAG, "Error Occurred:\n${throwable.message}")
                    _viewState.value = LoadState.Error
                }
            )
            .also { disposables.add(it) }
    }

    // TODO - Make inserting to Match and Player DAOs a single Transaction
    fun insertMatch(match: Match) {
        createOrUpdatePlayer(
            playerName = match.player1Name,
            playerWon = match.player1Score > match.player2Score
        )

        createOrUpdatePlayer(
            playerName = match.player2Name,
            playerWon = match.player2Score > match.player1Score
        )

        matchRepository.insert(match)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Refresh the UI
                    loadMatches()
                },
                { throwable ->
                    Log.e(TAG, "Error Occurred:\n${throwable.message}")
                }
            )
            .also { disposables.add(it) }
    }

    private fun createOrUpdatePlayer(
        playerName: String,
        playerWon: Boolean
    ) {
        val increment = if (playerWon) 1 else 0
        playerRepository.getPlayerByName(playerName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { player ->
                    // Player found in Database
                    insertPlayer(
                        Player(
                            id = player.id,
                            name = player.name,
                            matchesPlayed = player.matchesPlayed + 1,
                            matchesWon = player.matchesWon + increment
                        )
                    )
                },
                {
                    // Error inserting Player
                    Log.e(TAG, "${it.message}")
                    // TODO Show Snackbar
                },
                {
                    // Player NOT found in Database - create new entry
                    insertPlayer(
                        Player(
                            id = UUID.randomUUID().toString(),
                            name = playerName,
                            matchesPlayed = 1,
                            matchesWon = increment
                        )
                    )
                }
            )
            .also { disposables.add(it) }
    }

    private fun insertPlayer(player: Player) {
        playerRepository
            .insert(player)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                { throwable ->
                    Log.e(TAG, "${throwable.message}")
                }
            )
            .also { disposables.add(it) }
    }

    fun deleteMatch(match: Match) {
        matchRepository.delete(match)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Refresh the UI
                    loadMatches()
                },
                { throwable ->
                    Log.e(TAG, "${throwable.message}")
                }
            )
            .also { disposables.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
