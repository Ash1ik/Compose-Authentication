package org.example.project

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.example.project.SessionManager
import ui.screen.Login
import ui.screen.Profile
import ui.screen.Registration

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        val name = SessionManager.getUserName()
        val email = SessionManager.getUserEmail()
        if (SessionManager.isLoggedIn()) {
            navController.navigate("Profile/$name/$email") {
                popUpTo("Launcher") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("Launcher") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = "Launcher") {

        composable("Launcher") {
            // This screen is just a launching trampoline and can be left empty
        }

        composable("Registration") {
            Registration(
                onBackClick = { navController.popBackStack() },
                navController
            )
        }

        composable("login") {
            Login(
                navController = navController
            )
        }

        composable(
            route = "Profile/{name}/{email}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            Profile(
                userName = name,
                userEmail = email,
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
