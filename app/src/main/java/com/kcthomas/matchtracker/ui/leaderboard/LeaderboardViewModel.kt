package com.kcthomas.matchtracker.ui.leaderboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kcthomas.core.util.LoadState
import com.kcthomas.data.player.PlayerRepositoryImpl
import com.kcthomas.domain.player.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val repository: PlayerRepositoryImpl
) : ViewModel() {

    companion object {
        val TAG = LeaderboardViewModel::class.simpleName
    }

    private val _viewState = MutableLiveData<LoadState<List<Player>>>(LoadState.InFlight)
    val viewState
        get() = _viewState

    private val _orderState = MutableLiveData(OrderBy.MatchesWon)
    val orderState
        get() = _orderState

    init {
        loadPlayers()
    }

    private fun loadPlayers() {

        _viewState.value = LoadState.InFlight
        repository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { players ->
                    _viewState.value = LoadState.Success(players)
                    reorderPlayers(_orderState.value!!)
                },
                { throwable ->
                    Log.w(TAG, "${throwable.message}")
                    _viewState.value = LoadState.Error
                }
            )

    }

    fun updateOrderState(orderBy: OrderBy) {
        _orderState.value = orderBy
        reorderPlayers(orderBy)
    }

    private fun reorderPlayers(orderBy: OrderBy) {
        val state = _viewState.value
        if (state is LoadState.Success) {
            val players = state.data.sortedWith { player1, player2 ->
                when (orderBy) {
                    OrderBy.MatchesPlayed -> {
                        player2.matchesPlayed.toInt() - player1.matchesPlayed.toInt()
                    }
                    OrderBy.MatchesWon -> {
                        player2.matchesWon.toInt() - player1.matchesWon.toInt()
                    }
                    OrderBy.WinRate -> {
                        player2.matchesWon.toInt() / player2.matchesPlayed.toInt()
                        - player1.matchesWon.toInt() / player1.matchesPlayed.toInt()
                    }
                }
            }

            _viewState.value = LoadState.Success(players)
        }
    }

}
