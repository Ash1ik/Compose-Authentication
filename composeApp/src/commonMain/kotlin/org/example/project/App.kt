package org.example.project

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screen.Login
import ui.screen.Registration


@Composable
@Preview
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Registration") {

        composable("Registration") {
            Registration(navController)
        }

        composable("login") {
            Login(
                onBackClick = { navController.popBackStack() }
            )
        }
    }

}
