package uz.maverick.movieexplorerdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.maverick.movieexplorerdemo.presentation.screens.DetailScreen
import uz.maverick.movieexplorerdemo.presentation.screens.FavoriteScreen
import uz.maverick.movieexplorerdemo.presentation.screens.HomeScreen
import uz.maverick.movieexplorerdemo.presentation.screens.SearchScreen
import uz.maverick.movieexplorerdemo.utils.Logger

@Composable
fun SetupNavController(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Destinations.HomeScreen.label) {
        composable(route = Destinations.HomeScreen.label) {
            HomeScreen(navController = navController)
        }
        composable(route = Destinations.FavoriteScreen.label) {
            FavoriteScreen(navController = navController)
        }
        composable(
            route = Destinations.DetailScreen.label,
            arguments = listOf(navArgument("id") {
                type = androidx.navigation.NavType.IntType
                defaultValue = -1
                nullable = false
            })
        ) {
            val movieId = it.arguments?.getInt("id") ?: -1
            Logger.d("Movie ID: $movieId")
            DetailScreen(navController = navController, movieId = movieId)
        }
        composable(route = Destinations.SearchScreen.label) {
            SearchScreen(navController = navController)
        }
    }
}