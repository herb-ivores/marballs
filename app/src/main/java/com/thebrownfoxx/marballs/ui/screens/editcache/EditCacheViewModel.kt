package com.thebrownfoxx.marballs.ui.screens.editcache

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.extensions.mapToStateFlow
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.map.LocationProvider
import com.thebrownfoxx.marballs.ui.screens.navArgs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditCacheViewModel(
    private val locationProvider: LocationProvider,
    private val cacheInfoProvider: CacheInfoProvider,
    private val cacheRepository: CacheRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val cacheId = savedStateHandle.navArgs<CacheNavArgs>().CacheId
    private val _cache = MutableStateFlow<Cache?>(null)
    val cache: StateFlow<Cache?> = _cache.asStateFlow()

    init {
        viewModelScope.launch {
            _cache.emit(cacheRepository.getCache(cacheId))
        }
    }

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val location = locationProvider.currentLocation

    val locationName = location.mapToStateFlow(scope = viewModelScope) {
        with(cacheInfoProvider) {
            (it ?: Location(0.0, 0.0)).getLocationName()
        }
    }

    private val _successful = MutableSharedFlow<Unit>()
    val successful = _successful.asSharedFlow()

    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    fun setName(name: String) {
        _cache.update { it?.copy(name = name) }
    }

    fun setDescription(description: String) {
        _cache.update { it?.copy(description = description) }
    }

    fun resetLocation() {
        locationProvider.updateLocation()
    }

    fun update(location: Location) {
        val cache = cache.value
        if (cache!!.name.isBlank() || cache.description.isBlank()) {
            viewModelScope.launch {
                _errors.emit("Please fill out all fields")
            }
            _loading.value = false
            return
        }
        val newCache = Cache(
            id = cache.id,
            name = cache.name,
            authorUid = cache.authorUid,
            description = cache.description,
            location = location
        )
        cacheRepository.updateCache(newCache) { result ->
            when (result) {
                is Outcome.Success -> {
                    viewModelScope.launch {
                        _successful.emit(Unit)
                    }
                }

                is Outcome.Failure -> {
                    viewModelScope.launch {
                        _errors.emit(result.throwableMessage)
                    }
                }
            }
        }
    }
}