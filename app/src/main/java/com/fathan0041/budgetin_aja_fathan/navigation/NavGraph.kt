package com.fathan0041.budgetin_aja_fathan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fathan0041.budgetin_aja_fathan.ui.screen.AboutScreen
import com.fathan0041.budgetin_aja_fathan.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route=Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route=Screen.About.route) {
            AboutScreen(navController)
        }
    }
}