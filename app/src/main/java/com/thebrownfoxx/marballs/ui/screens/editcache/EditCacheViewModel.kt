package com.thebrownfoxx.marballs.ui.screens.editcache

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.location.LocationProvider
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
            when (val outcome = cacheRepository.getCache(cacheId)) {
                is Outcome.Success -> _cache.value = outcome.data
                is Outcome.Failure -> TODO("Show an error snackbar")
            }
        }
    }

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val location = locationProvider.currentLocation

    private val _locationName = MutableStateFlow("")
    val locationName = _locationName.asStateFlow()

    init {
        viewModelScope.launch {
            locationProvider.currentLocation.collect {
                val outcome = with(cacheInfoProvider) {
                    it?.getLocationName()
                }
                if (outcome is Outcome.Success && outcome.data != "") {
                    _locationName.value = outcome.data
                }
            }
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
        viewModelScope.launch {
            locationProvider.updateLocation()
        }
    }

    fun update(location: Location) {
        // TODO: Add loading indicator
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

        viewModelScope.launch {
            when (val outcome = cacheRepository.updateCache(newCache)) {
                is Outcome.Success -> {
                    viewModelScope.launch {
                        _successful.emit(Unit)
                    }
                }

                is Outcome.Failure -> {
                    viewModelScope.launch {
                        _errors.emit(outcome.throwableMessage)
                    }
                }
            }
        }
    }
}