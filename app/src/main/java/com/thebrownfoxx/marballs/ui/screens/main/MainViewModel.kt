package com.thebrownfoxx.marballs.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.extensions.combineToStateFlow
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoService
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.map.Map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val authentication: Authentication,
    private val map: Map,
    private val cacheRepository: CacheRepository,
    private val cacheInfoService: CacheInfoService,
): ViewModel() {
    val loggedIn = authentication.loggedIn

    private val _currentScreen = MutableStateFlow(MainScreen.Map)
    val currentScreen = _currentScreen.asStateFlow()

    init {
        map.updateLocation()
    }

    fun navigateTo(screen: MainScreen) {
        _currentScreen.value = screen
    }

    fun logout() {
        authentication.logout()
    }

    // Map screen
    val currentLocation = map.currentLocation

    private val _selectedCache = MutableStateFlow<CacheInfo?>(null)
    val selectedCache = _selectedCache.asStateFlow()

    fun resetLocation() {
        map.updateLocation()
    }

    // Caches screen
    val caches = with(cacheInfoService) {
        combineToStateFlow(
            cacheRepository.caches,
            map.currentLocation,
            scope = viewModelScope,
        ) { caches, currentLocation ->
            caches.map { cache ->
                cache.toCacheInfo(currentLocation ?: Location(0.0, 0.0))
            }
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCache(cache: CacheInfo) {
        _currentScreen.value = MainScreen.Map
        _selectedCache.value = cache
    }
}