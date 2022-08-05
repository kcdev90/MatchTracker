package com.kcthomas.matchtracker.ui.leaderboard

import com.kcthomas.core.R

enum class OrderBy {
    MatchesPlayed,
    MatchesWon,
    WinRate
}

fun OrderBy.getResId() = when (this) {
    OrderBy.MatchesPlayed -> R.string.matches_played
    OrderBy.MatchesWon -> R.string.matches_won
    OrderBy.WinRate -> R.string.win_rate
}
