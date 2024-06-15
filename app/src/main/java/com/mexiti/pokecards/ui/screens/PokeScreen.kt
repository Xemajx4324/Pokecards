package com.mexiti.pokecards.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mexiti.pokecards.model.PokeCard
import com.mexiti.pokecards.viewmodel.PokeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeScreen(viewModel: PokeViewModel) {
    val cards by viewModel.pokeCards.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PokeCards Firebase", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFCC01), // Amarillo Pokémon
                    titleContentColor = Color(0xFF2A75BB) // Azul Pokémon
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cards) { card ->
                PokeCardItem(
                    card = card,
                    onCatchClick = { viewModel.toggleCaught(card) },
                    onHoloClick = { viewModel.toggleHolo(card) }
                )
            }
        }
    }
}

@Composable
fun PokeCardItem(
    card: PokeCard,
    onCatchClick: () -> Unit,
    onHoloClick: () -> Unit
) {
    // Formatear la fecha (timestamp) a texto legible
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateString = try {
        dateFormat.format(Date(card.timestamp))
    } catch (e: Exception) {
        "Fecha desconocida"
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen desde URL
            AsyncImage(
                model = card.imageUrl,
                contentDescription = card.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Fila del Nombre y la Estrella
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = card.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    // --- ESTRELLA DE "HOLO" ---
                    IconButton(onClick = onHoloClick) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Holográfica",
                            // El color es lo que nos dice si está activa o no
                            tint = if (card.isHolo) Color(0xFFFFD700) else Color.LightGray
                        )
                    }
                }

                Text(text = "Tipo: ${card.type}", color = Color.Gray, fontSize = 14.sp)
                Text(text = "HP: ${card.hp}", fontWeight = FontWeight.Bold, color = Color.Red)

                // Mostrar la fecha
                Text(text = "Obtenida: $dateString", fontSize = 10.sp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(4.dp))

                // Indicador visual de captura
                if (card.isCaught) {
                    Text(text = "✅ CAPTURADO", color = Color(0xFF4CAF50), fontWeight = FontWeight.Black, fontSize = 12.sp)
                } else {
                    Text(text = "❌ Faltante", color = Color.Gray, fontSize = 12.sp)
                }
            }

            // Checkbox para modificar "isCaught"
            Checkbox(
                checked = card.isCaught,
                onCheckedChange = { onCatchClick() }
            )
        }
    }
}