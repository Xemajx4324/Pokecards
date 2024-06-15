package com.mexiti.pokecards.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mexiti.pokecards.model.PokeCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PokeViewModel : ViewModel() {
    private val db = Firebase.firestore

    // Estado de la lista de cartas
    private val _pokeCards = MutableStateFlow<List<PokeCard>>(emptyList())
    val pokeCards: StateFlow<List<PokeCard>> = _pokeCards.asStateFlow()

    init {
        fetchCards()
    }

    // Leer datos de Firebase en tiempo real
    private fun fetchCards() {
        db.collection("PokeCards")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("FirebaseError", "Error al escuchar datos", error)
                    return@addSnapshotListener
                }

                val list = mutableListOf<PokeCard>()
                if (value != null) {
                    for (document in value) {
                        val card = document.toObject(PokeCard::class.java)
                            .copy(idDoc = document.id)
                        list.add(card)
                    }
                }
                _pokeCards.value = list
            }
    }

    // Modificar datos en Firebase (Atrapar/Liberar)
    fun toggleCaught(card: PokeCard) {
        val newStatus = !card.isCaught
        db.collection("PokeCards").document(card.idDoc)
            .update("isCaught", newStatus)
            .addOnSuccessListener { Log.d("Firebase", "Estado actualizado") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error al actualizar", e) }
    }
    fun toggleHolo(card: PokeCard) {
        val newStatus = !card.isHolo
        // Actualizamos solo el campo "isHolo" en Firebase
        db.collection("PokeCards").document(card.idDoc)
            .update("isHolo", newStatus)
            .addOnSuccessListener { Log.d("Firebase", "Holo actualizado") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error al actualizar Holo", e) }
    }
}