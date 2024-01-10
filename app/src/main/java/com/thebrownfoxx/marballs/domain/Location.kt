package com.thebrownfoxx.marballs.domain

import kotlin.random.Random

data class Location(
    val latitude: Double,
    val longitude: Double,
    val key: Long = Random.nextLong(),
)