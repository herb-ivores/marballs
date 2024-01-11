package com.thebrownfoxx.marballs.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.extensions.combineToStateFlow
import com.thebrownfoxx.extensions.search
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.FindInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.findinfo.FindInfoProvider
import com.thebrownfoxx.marballs.services.finds.FindsRepository
import com.thebrownfoxx.marballs.services.location.LocationProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authentication: Authentication,
    private val locationProvider: LocationProvider,
    private val cacheRepository: CacheRepository,
    private val cacheInfoProvider: CacheInfoProvider,
    private val findsRepository: FindsRepository,
    private val findInfoProvider: FindInfoProvider,
) : ViewModel() {
    val loggedIn = authentication.loggedIn

    private val _currentScreen = MutableStateFlow(MainScreen.Map)
    val currentScreen = _currentScreen.asStateFlow()

    init {
        viewModelScope.launch {
            locationProvider.updateLocation()
        }
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

    val selectedCacheFound = combineToStateFlow(
        authentication.currentUser,
        selectedCache,
        findsRepository.finds,
        scope = viewModelScope,
    ) { currentUser, selectedCache, finds ->
        finds?.any { it.cacheId == selectedCache?.id && it.finderId == currentUser?.uid } ?: false
    }

    fun markSelectedCacheAsFound() {
        val selectedCache = selectedCache.value
        val currentUser = authentication.currentUser.value
        if (selectedCache != null && currentUser != null) {
            viewModelScope.launch {
                val outcome = findsRepository.addFind(
                    Find(
                        cacheId = selectedCache.id,
                        finderId = currentUser.uid,
                        foundEpochMillis = System.currentTimeMillis(),
                    )
                )
                if (outcome is Outcome.Failure) {
                    viewModelScope.launch {
                        _findsErrors.emit(outcome.throwableMessage)
                    }
                }
            }
        }
    }

    fun unmarkSelectedCacheAsFound() {
        val selectedCache = selectedCache.value
        val currentUser = authentication.currentUser.value
        if (selectedCache != null && currentUser != null) {
//            findsRepository.removeFind(selectedCache.id) { outcome ->
//                if (outcome is Outcome.Failure) {
//                    viewModelScope.launch {
//                        _findsErrors.emit(outcome.throwableMessage)
//                    }
//                }
//            }
            viewModelScope.launch {
                val outcome = findsRepository.removeFind(selectedCache.id)
                if (outcome is Outcome.Failure) {
                    viewModelScope.launch {
                        _findsErrors.emit(outcome.throwableMessage)
                    }
                }
            }
        }
    }

    fun resetLocation() {
        viewModelScope.launch {
            locationProvider.updateLocation()
        }
    }

    // Caches screen
    private val _cachesSearchQuery = MutableStateFlow("")
    val cachesSearchQuery = _cachesSearchQuery.asStateFlow()

    private val _caches = MutableStateFlow<List<CacheInfo>?>(null)
    val caches = _caches.asStateFlow()

    /*    val caches = combineToStateFlow(
            cachesSearchQuery,
            cacheRepository.caches,
            locationProvider.currentLocation,
            scope = viewModelScope,
        ) { searchQuery, caches, currentLocation ->
            with(cacheInfoProvider) {
                caches?.map { cache ->
                    cache.toCacheInfo(currentLocation ?: Location(0.0, 0.0))
                }?.search(searchQuery) { it.name }
            }
        }*/
    init {
        viewModelScope.launch {
            cachesSearchQuery.collect { searchQuery ->
                locationProvider.currentLocation.collect { currentLocation ->
                    cacheRepository.caches.collect { caches ->
                        val outcome = with(cacheInfoProvider) {
                            caches?.map { cache ->
                                cache.toCacheInfo(currentLocation ?: Location(0.0, 0.0))
                            }
                        }
                        if (outcome?.all { it is Outcome.Success } == true) {
                            _caches.value = outcome.map { (it as Outcome.Success).data }
                                .search(searchQuery) { it.name }
                        }
                    }
                }
            }
        }
    }

    fun setCachesSearchQuery(query: String) {
        _cachesSearchQuery.value = query
    }

    fun selectCache(cache: CacheInfo) {
        _currentScreen.value = MainScreen.Map
        _selectedCache.value = cache
    }

    // Finds screen
    private val _findsErrors = MutableSharedFlow<String>()
    val findsErrors = _findsErrors.asSharedFlow()

    private val _findsSearchQuery = MutableStateFlow("")
    val findsSearchQuery = _findsSearchQuery.asStateFlow()

    private val _finds = MutableStateFlow<List<FindInfo>?>(null)
    val finds = _finds.asStateFlow()

    init {
        viewModelScope.launch {
            findsSearchQuery.collect { searchQuery ->
                findsRepository.finds.collect { finds ->
                    val outcome = with(findInfoProvider) {
                        finds?.map { find ->
                            find.toFindInfo()
                        }
                    }
                    if (outcome?.all { it is Outcome.Success } == true) {
                        _finds.value = outcome.map { (it as Outcome.Success).data }
                            .search(searchQuery) { it.cache.name }
                    }
                }
            }
        }
    }

    fun setFindsSearchQuery(query: String) {
        _findsSearchQuery.value = query
    }

    fun selectFind(find: FindInfo) {
        _currentScreen.value = MainScreen.Map
        _selectedCache.value = find.cache
    }

    fun unmarkFindAsFound(find: FindInfo) {
        viewModelScope.launch {
            val outcome = findsRepository.removeFind(find.cache.id)
            if (outcome is Outcome.Failure) {
                viewModelScope.launch {
                    _findsErrors.emit(outcome.throwableMessage)
                }
            }
        }
    }
}