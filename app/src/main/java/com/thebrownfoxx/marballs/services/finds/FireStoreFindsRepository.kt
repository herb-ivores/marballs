package com.thebrownfoxx.marballs.services.finds

import com.google.firebase.firestore.FirebaseFirestore
import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.awaitOutcome
import com.thebrownfoxx.marballs.services.awaitUnitOutcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FireStoreFindsRepository(private val firestore: FirebaseFirestore) : FindsRepository {
    val scope = CoroutineScope(Dispatchers.IO)

    private val _finds = MutableStateFlow(emptyList<Find>())
    override val finds = _finds.asStateFlow()

    init {
        scope.launch {
            updateFinds()
        }
    }

    override suspend fun updateFinds() {
        withContext(Dispatchers.IO) {
            firestore.collection("finds")
                .get()
                .addOnSuccessListener { results ->
                    _finds.value = results.map {
                        Find(
                            id = it.id,
                            cacheId = it.getString("cacheId") ?: "",
                            finderId = it.getString("finderId") ?: "",
                            foundEpochMillis = it.getLong("found") ?: 0
                        )
                    }
                }.await()
        }
    }

    override suspend fun addFind(find: Find): Outcome<Unit> = withContext(Dispatchers.IO) {
        val findMap = mapOf(
            "cacheId" to find.cacheId,
            "finderId" to find.finderId,
            "found" to find.foundEpochMillis,
        )
        return@withContext firestore.collection("finds")
            .add(findMap)
            .awaitUnitOutcome()
            .also { updateFinds() }
    }

    override suspend fun removeFind(cacheId: String): Outcome<Unit> = withContext(Dispatchers.IO) {
        val outcome = firestore.collection("finds").whereEqualTo("cacheId", cacheId)
            .get()
            .awaitOutcome()
        if (outcome is Outcome.Failure) {
            return@withContext Outcome.Failure(outcome.throwable)
        }
        (outcome as Outcome.Success).data.first().reference.delete().awaitUnitOutcome()
            .also { updateFinds() }
    }

}