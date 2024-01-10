package com.thebrownfoxx.marballs.services.caches

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.addOnOutcomeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FirestoreCacheRepository(private val firestore: FirebaseFirestore) : CacheRepository {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _caches = MutableStateFlow<List<Cache>?>(null)
    override val caches = _caches.asStateFlow()

    init {
        scope.launch {
            fetchCaches()
        }
    }

    suspend fun fetchCaches() {
        firestore.collection("tasks").snapshots().map { querySnapshot->
            querySnapshot.toObjects(Cache::class.java)
        }.collect{ _caches.value = it}
    }


    override fun addCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {

        firestore.collection("caches")
            .add(cache)
            .addOnOutcomeListener(onOutcomeReceived)
    }

    override fun updateCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {

        firestore.collection("caches").document(cache.id)
            .set(cache)
            .addOnOutcomeListener(onOutcomeReceived)
    }

    override fun removeCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        firestore.collection("caches").document(cache.id)
            .delete()
            .addOnOutcomeListener(onOutcomeReceived)
    }
}