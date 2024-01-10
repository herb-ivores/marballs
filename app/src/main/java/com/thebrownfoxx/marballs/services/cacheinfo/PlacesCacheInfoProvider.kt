package com.thebrownfoxx.marballs.services.cacheinfo

import com.google.android.libraries.places.api.net.PlacesClient
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location

class PlacesCacheInfoProvider(private val placesClient: PlacesClient) : CacheInfoProvider {
    override fun Cache.toCacheInfo(currentLocation: Location): CacheInfo {
        TODO("Not yet implemented")
    }
}