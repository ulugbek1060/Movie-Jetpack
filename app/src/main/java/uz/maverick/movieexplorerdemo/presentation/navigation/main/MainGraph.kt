package uz.maverick.movieexplorerdemo.presentation.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import uz.maverick.movieexplorerdemo.presentation.screens.main.DetailScreen
import uz.maverick.movieexplorerdemo.presentation.screens.main.MainScreen

/**
 * Created by Maverick on 10/20/2023.
 */
const val MAIN_ROUTE = "main_graph"

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(
        startDestination = MainDestinations.MainScreen.route,
        route = MAIN_ROUTE
    ) {
        composable(
            route = MainDestinations.MainScreen.route,
            content = {
                MainScreen(rootNavController = navController)
            }
        )
        composable(
            route = MainDestinations.MovieDetailsScreen.route,
            arguments = listOf(navArgument("movieId") {
                type = androidx.navigation.NavType.IntType
                defaultValue = 0
            }),
            content = { backStackEntry ->
                DetailScreen(
                    navController,
                    movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                )
            }
        )
    }
}