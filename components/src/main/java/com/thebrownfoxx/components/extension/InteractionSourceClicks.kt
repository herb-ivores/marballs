package com.thebrownfoxx.components.extension

import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

val InteractionSource.clicks: Flow<Unit>
    @Composable
    get() {
        val clicks = MutableSharedFlow<Unit>()
        var timePressed by rememberMutableStateOf<Long?>(null)

        LaunchedEffect(this) {
            interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> timePressed = System.currentTimeMillis()
                    is PressInteraction.Release -> {
                        val currentTimePressed = timePressed
                        if (currentTimePressed != null) {
                            val currentTime = System.currentTimeMillis()
                            val pressDuration = currentTime - currentTimePressed
                            if (pressDuration < 500) {
                                clicks.emit(Unit)
                            }
                        }
                        timePressed = null
                    }

                    is DragInteraction.Cancel -> timePressed = null
                }
            }
        }

        return clicks
    }