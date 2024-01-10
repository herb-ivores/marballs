package com.thebrownfoxx.marballs.services.cacheinfo

import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.extensions.distanceTo

class DummyCacheInfoService: CacheInfoService {
    override fun Cache.toCacheInfo(currentLocation: Location): CacheInfo {
        return CacheInfo(
            id = id,
            name = name,
            description = description,
            location = listOf("Area 69", "Test Tease City").random(),
            distance = currentLocation.distanceTo(location),
        )
    }
}