package com.thebrownfoxx.marballs.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.extensions.combineToStateFlow
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.map.LocationProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val authentication: Authentication,
    private val locationProvider: LocationProvider,
    private val cacheRepository: CacheRepository,
    private val cacheInfoProvider: CacheInfoProvider,
): ViewModel() {
    val loggedIn = authentication.loggedIn

    private val _currentScreen = MutableStateFlow(MainScreen.Map)
    val currentScreen = _currentScreen.asStateFlow()

    init {
        locationProvider.updateLocation()
    }

    fun navigateTo(screen: MainScreen) {
        _currentScreen.value = screen
    }

    fun logout() {
        authentication.logout()
    }

    // Map screen
    val currentLocation = locationProvider.currentLocation

    private val _selectedCache = MutableStateFlow<CacheInfo?>(null)
    val selectedCache = _selectedCache.asStateFlow()

    val allowCacheEdit = combineToStateFlow(
        authentication.currentUser,
        selectedCache,
        scope = viewModelScope,
    ) { currentUser, selectedCache ->
        currentUser == selectedCache?.author
    }

    fun resetLocation() {
        locationProvider.updateLocation()
    }

    // Caches screen
    val caches = with(cacheInfoProvider) {
        combineToStateFlow(
            cacheRepository.caches,
            locationProvider.currentLocation,
            scope = viewModelScope,
        ) { caches, currentLocation ->
            caches?.map { cache ->
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