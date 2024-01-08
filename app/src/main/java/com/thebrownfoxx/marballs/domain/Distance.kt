package com.thebrownfoxx.marballs.domain

import kotlin.math.roundToInt

data class Distance(val meters: Double) {
    val kilometers: Double = meters / 1000.0
    override fun toString() = when {
        kilometers >= 1 -> "${kilometers.roundToInt()} km"
        else -> "${meters.roundToInt()} m"
    }
}

val Double.meters get() = Distance(this)