package com.thebrownfoxx.marballs.domain

sealed class Outcome<T> {
    data class Success<T>(val data: T) : Outcome<T>()
    data class Failure<T>(val throwable: Throwable) : Outcome<T>() {
        val throwableMessage = throwable.message ?: "An unknown error occurred"
    }

    companion object {
        fun Success() = Success(Unit)
    }
}