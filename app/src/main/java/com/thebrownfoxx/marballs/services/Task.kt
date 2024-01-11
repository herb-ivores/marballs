package com.thebrownfoxx.marballs.services

import android.util.Log
import com.google.android.gms.tasks.Task
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

fun <T> Task<T>.addOnOutcomeListener(onOutcomeReceived: (Outcome<Unit>) -> Unit) {
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onOutcomeReceived(Outcome.Success())
        } else {
            onOutcomeReceived(Outcome.Failure(task.exception ?: Exception("Unknown exception")))
        }
    }
    addOnFailureListener { exception ->
        onOutcomeReceived(Outcome.Failure(exception))
    }
}

suspend fun <T> Task<T>.awaitOutcome(): Outcome<T> {
    val x = withContext(Dispatchers.IO) {
        try {
            Outcome.Success(await())
        } catch (e: Exception) {
            Outcome.Failure(e)
        }
    }
    Log.d(this::class.simpleName, "awaitOutcome: $x")
    return x
}

suspend fun <T> Task<T>.awaitUnitOutcome() = awaitOutcome().map { }