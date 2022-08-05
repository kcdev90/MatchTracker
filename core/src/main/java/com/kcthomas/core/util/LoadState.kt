package com.kcthomas.core.util

// A general purpose UI state and a wrapper for some underlying data T
sealed class LoadState <out T> {
    object InFlight : LoadState<Nothing>()
    object Error : LoadState<Nothing>()
    data class Success<out T>(val data: T) : LoadState<T>()
}
