package com.thebrownfoxx.marballs.extensions

import com.google.android.gms.maps.model.LatLng
import com.thebrownfoxx.marballs.domain.Location

fun Location.toLatLng() = LatLng(latitude, longitude)