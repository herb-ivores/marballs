package com.thebrownfoxx.marballs.ui.extensions

import com.google.android.gms.maps.model.LatLng
import com.thebrownfoxx.marballs.domain.Location

fun LatLng.toLocation() = Location(
    latitude = latitude,
    longitude = longitude,
)