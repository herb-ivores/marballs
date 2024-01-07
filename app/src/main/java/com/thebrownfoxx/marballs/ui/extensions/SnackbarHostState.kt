package com.thebrownfoxx.marballs.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SnackbarHostState(private val scope: CoroutineScope) {
    val snackbarHostState = androidx.compose.material3.SnackbarHostState()
    private var snackbarJob: Job? = null

    fun showSnackbarMessage(message: String) {
        snackbarJob?.cancel()
        snackbarJob = scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    fun hideSnackbarMessage() {
        snackbarJob?.cancel()
    }
}

@Composable
fun rememberSnackbarHostState(): SnackbarHostState {
    val scope = rememberCoroutineScope()
    return remember { SnackbarHostState(scope) }
}

@Composable
fun rememberSnackbarHostState(messages: Flow<String>): SnackbarHostState {
    val state = rememberSnackbarHostState()

    LaunchedEffect(messages) {
        messages.collect { message ->
            state.showSnackbarMessage(message)
        }
    }

    return state
}