package com.mexiti.pokecards.model

import com.google.firebase.firestore.PropertyName

data class PokeCard(
    val idDoc: String = "",
    val name: String = "",
    val type: String = "",
    val hp: Int = 0,
    val imageUrl: String = "",

    @get:PropertyName("isCaught")
    val isCaught: Boolean = false,

    // --- NUEVOS CAMPOS ---
    @get:PropertyName("isHolo")
    val isHolo: Boolean = false, // ¿Es carta brillante?

    val timestamp: Long = System.currentTimeMillis() // Fecha de obtención (Tipo Long)
)

// este es un mensaje para checar si el commit funciono no hacerle caso hola mundo