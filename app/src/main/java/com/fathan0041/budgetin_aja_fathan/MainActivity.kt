package com.fathan0041.budgetin_aja_fathan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fathan0041.budgetin_aja_fathan.ui.screen.MainScreen
import com.fathan0041.budgetin_aja_fathan.ui.theme.BudgetinAja_FathanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetinAja_FathanTheme {
                MainScreen()
            }
        }
    }
}

