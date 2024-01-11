package com.thebrownfoxx.marballs.domain

sealed class Outcome<T> {
    data class Success<T>(val data: T) : Outcome<T>()
    data class Failure<T>(val throwable: Throwable) : Outcome<T>() {
        val throwableMessage = throwable.message ?: "An unknown error occurred"
    }

    companion object {
        fun Success() = Success(Unit)
    }

    fun <R> map(transform: (T) -> R): Outcome<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(throwable)
        }
    }
}