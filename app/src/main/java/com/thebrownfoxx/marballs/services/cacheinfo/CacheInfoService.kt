package com.thebrownfoxx.marballs.services.cacheinfo

import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location

interface CacheInfoService {
    fun Cache.toCacheInfo(currentLocation: Location): CacheInfo
}