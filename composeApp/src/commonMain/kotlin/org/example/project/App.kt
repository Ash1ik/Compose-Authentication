package org.example.project

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screen.Login
import ui.screen.Profile
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
                onBackClick = { navController.popBackStack() },
                navController
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
                onBackClick = { navController.popBackStack() }
            )
        }
    }

}
