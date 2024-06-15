package com.mexiti.pokecards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mexiti.pokecards.ui.screens.PokeScreen
import com.mexiti.pokecards.viewmodel.PokeViewModel
import com.mexiti.pokecards.ui.theme.FoodAppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            FoodAppTheme {
                val viewModel: PokeViewModel = viewModel()
                PokeScreen(viewModel = viewModel)
            }
        }
    }
}