package uz.maverick.movieexplorerdemo.presentation.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import uz.maverick.movieexplorerdemo.presentation.screens.auth.RegisterScreen

/**
 * Created by Maverick on 10/20/2023.
 */

const val AUTH_ROUTE = "auth_graph"

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthDestinations.RegisterScreen.route,
        route = AUTH_ROUTE
    ) {
        composable(
            route = AuthDestinations.RegisterScreen.route,
            content = { RegisterScreen(navController) }
        )
    }
}
