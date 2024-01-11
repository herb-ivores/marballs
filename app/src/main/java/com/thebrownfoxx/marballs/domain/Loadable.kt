package com.thebrownfoxx.marballs.domain

sealed class Loadable<T> {
    class Loading<T> : Loadable<T>()
    data class Success<T>(val data: T) : Loadable<T>()
    data class Failure<T>(val error: Throwable) : Loadable<T>()
}