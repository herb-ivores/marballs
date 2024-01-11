package com.thebrownfoxx.marballs.services.cacheinfo

import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User

interface CacheInfoProvider {
    suspend fun Cache.toCacheInfo(currentLocation: Location): Outcome<CacheInfo>
    suspend fun Location.getLocationName(): Outcome<String>
}