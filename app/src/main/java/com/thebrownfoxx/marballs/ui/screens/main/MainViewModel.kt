package com.thebrownfoxx.marballs.ui.screens.main

import android.util.Log
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val authentication: Authentication,
    private val locationProvider: LocationProvider,
    private val cacheRepository: CacheRepository,
    private val cacheInfoProvider: CacheInfoProvider,
    private val findsRepository: FindsRepository,
    private val findInfoProvider: FindInfoProvider,
) : ViewModel() {
    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    val loggedIn = authentication.loggedIn

    val currentUser = authentication.currentUser

    private val reload = MutableSharedFlow<Unit>()

    private val _currentScreen = MutableStateFlow(MainScreen.Map)
    val currentScreen = _currentScreen.asStateFlow()

    fun navigateTo(screen: MainScreen) {
        _currentScreen.value = screen
    }

    // Map screen
    private val _location = MutableStateFlow(locationProvider.currentLocation.value)
    val location = _location.asStateFlow()

    init {
        resetLocation()
    }

    private val selectedCacheId = MutableStateFlow<String?>(null)
    val selectedCache = combine(
        selectedCacheId,
        cacheRepository.caches,
    ) { selectedCacheId, caches ->
        with(cacheInfoProvider) {
            val outcome = caches?.find { it.id == selectedCacheId }?.toCacheInfo(
                locationProvider.currentLocation.value ?: Location(0.0, 0.0)
            )
            if (outcome is Outcome.Success) {
                outcome.data
            } else {
                null
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val allowCacheEdit = combineToStateFlow(
        authentication.currentUser,
        selectedCache,
        scope = viewModelScope,
    ) { currentUser, selectedCache ->
        currentUser?.uid == selectedCache?.author?.uid
    }

    val selectedCacheFound = combineToStateFlow(
        authentication.currentUser,
        selectedCache,
        findsRepository.finds,
        scope = viewModelScope,
    ) { currentUser, selectedCache, finds ->
        finds?.any { it.cacheId == selectedCache?.id && it.finderId == currentUser?.uid } ?: false
    }

    private val _deleteDialogVisible = MutableStateFlow(false)
    val deleteDialogVisible = _deleteDialogVisible.asStateFlow()

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
                        _errors.emit(outcome.throwableMessage)
                    }
                }
            }
        }
    }

    fun unmarkSelectedCacheAsFound() {
        val selectedCache = selectedCache.value
        val currentUser = authentication.currentUser.value
        if (selectedCache != null && currentUser != null) {
            viewModelScope.launch {
                val outcome = findsRepository.removeFind(selectedCache.id)
                if (outcome is Outcome.Failure) {
                    _errors.emit(outcome.throwableMessage)
                }
            }
        }
    }

    fun initiateDeleteCache() {
        if (selectedCache.value != null) {
            _deleteDialogVisible.value = true
        }
    }

    fun cancelDeleteCache() {
        _deleteDialogVisible.value = false
    }

    fun deleteCache() {
        val selectedCache = selectedCache.value
        if (selectedCache != null) {
            viewModelScope.launch {
                val outcome = cacheRepository.removeCache(selectedCache.id)
                if (outcome is Outcome.Failure) {
                    _errors.emit(outcome.throwableMessage)
                }
                _deleteDialogVisible.value = false
                selectedCacheId.value = null
            }
        }
    }

    fun resetLocation() {
        viewModelScope.launch {
            locationProvider.updateLocation()
            _location.value = locationProvider.currentLocation.value
        }
    }

    // Caches screen
    private val _cachesSearchQuery = MutableStateFlow("")
    val cachesSearchQuery = _cachesSearchQuery.asStateFlow()

    val caches = combine(
        cachesSearchQuery,
        cacheRepository.caches,
        locationProvider.currentLocation,
    ) { searchQuery, caches, currentLocation ->
        Log.d(this::class.simpleName, "caches: $caches")
        val outcomes = with(cacheInfoProvider) {
            caches?.map { cache ->
                cache.toCacheInfo(
                    currentLocation ?: Location(0.0, 0.0)
                )
            }
        }
        if (outcomes == null || outcomes.any { it is Outcome.Failure }) {
            emptyList()
        } else {
            outcomes.filterIsInstance<Outcome.Success<CacheInfo>>()
                .map { it.data }
                .search(searchQuery) { it.name }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setCachesSearchQuery(query: String) {
        _cachesSearchQuery.value = query
    }

    fun selectCache(cache: CacheInfo) {
        _currentScreen.value = MainScreen.Map
        selectedCacheId.value = cache.id
        _location.value = cache.coordinates
    }

    private val _findsSearchQuery = MutableStateFlow("")
    val findsSearchQuery = _findsSearchQuery.asStateFlow()

    val finds = combine(
        findsSearchQuery,
        findsRepository.finds,
    ) { searchQuery, finds ->
        val outcomes = with(findInfoProvider) {
            finds?.map { find ->
                find.toFindInfo()
            }
        }
        if (outcomes == null || outcomes.any { it is Outcome.Failure }) {
            emptyList()
        } else {
            outcomes.filterIsInstance<Outcome.Success<FindInfo>>()
                .map { it.data }
                .search(searchQuery) { it.cache.name }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setFindsSearchQuery(query: String) {
        _findsSearchQuery.value = query
    }

    fun selectFind(find: FindInfo) {
        _currentScreen.value = MainScreen.Map
        selectedCacheId.value = find.cache.id
    }

    fun unmarkFindAsFound(find: FindInfo) {
        viewModelScope.launch {
            val outcome = findsRepository.removeFind(find.cache.id)
            if (outcome is Outcome.Failure) {
                viewModelScope.launch {
                    _errors.emit(outcome.throwableMessage)
                }
            }
        }
    }

    fun logout() {
        authentication.logout()
    }

    fun reload() {
        viewModelScope.launch {
            cacheRepository.updateCaches()
            findsRepository.updateFinds()
            reload.emit(Unit)
        }
    }
}