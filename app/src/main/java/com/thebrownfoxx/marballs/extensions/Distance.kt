package com.thebrownfoxx.marballs.extensions

import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Distance
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.meters
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun Location.distanceTo(other: Location): Distance {
    val earthRadius = 6371000.0 //meters
    val latitudeDelta = Math.toRadians(other.latitude - this.latitude)
    val longitudeDelta = Math.toRadians(other.longitude - this.longitude)

    // Haversine formula
    val a = sin(latitudeDelta / 2) * sin(latitudeDelta / 2) +
            cos(Math.toRadians(this.latitude)) * cos(Math.toRadians(other.latitude)) *
            sin(longitudeDelta / 2) * sin(longitudeDelta / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a)) // great circle distance in radians
    return (earthRadius * c).meters
}

fun Cache.distanceFrom(location: Location): Distance = location.distanceTo(this.location)