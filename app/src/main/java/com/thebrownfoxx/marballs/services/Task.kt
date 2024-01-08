package com.thebrownfoxx.marballs.services

import com.google.android.gms.tasks.Task
import com.thebrownfoxx.marballs.domain.Outcome

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