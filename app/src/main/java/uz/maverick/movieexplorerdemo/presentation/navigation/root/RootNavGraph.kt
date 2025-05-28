package uz.maverick.movieexplorerdemo.presentation.navigation.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import uz.maverick.movieexplorerdemo.presentation.navigation.auth.authGraph
import uz.maverick.movieexplorerdemo.presentation.navigation.main.MAIN_ROUTE
import uz.maverick.movieexplorerdemo.presentation.navigation.main.mainGraph

/**
 * Created by Maverick on 10/20/2023.
 */

const val ROOT_ROUTE = "root_graph"

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MAIN_ROUTE,
        route = ROOT_ROUTE
    ) {
        authGraph(navController)
        mainGraph(navController)
    }
}



