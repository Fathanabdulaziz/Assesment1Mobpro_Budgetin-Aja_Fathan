package com.fathan0041.budgetin_aja_fathan.navigation

sealed class Screen (val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("AboutScreen")
}