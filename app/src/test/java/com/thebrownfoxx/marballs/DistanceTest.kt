package com.thebrownfoxx.marballs

import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.extensions.distanceTo
import org.junit.Test
import kotlin.math.roundToInt

class DistanceTest {
    @Test
    fun distanceTest() {
        val locationA = Location(50.0,50.0)
        val locationB = Location(65.0,65.0)
        val distance = locationA.distanceTo(locationB)
        println(distance)
        assert(distance.kilometers.roundToInt() in 1880..1882)
    }
}