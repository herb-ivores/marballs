package com.thebrownfoxx.marballs.services

import com.google.android.gms.tasks.Task

fun <T> Task<T>.addOnResultListener(onResult: (Result<Unit>) -> Unit) {
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onResult(Result.success(Unit))
        }
    }
    addOnFailureListener { exception ->
        onResult(Result.failure(exception))
    }
}