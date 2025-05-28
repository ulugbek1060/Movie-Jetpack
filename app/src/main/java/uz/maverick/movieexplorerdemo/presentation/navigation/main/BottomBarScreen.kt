package uz.maverick.movieexplorerdemo.presentation.navigation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector



sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector,
    val label: String,
) {
    object Home : BottomBarScreen(
        route = "home",
        icon = Icons.Rounded.Home,
        label = "Home"
    )
    object Search : BottomBarScreen(
        route = "search",
        icon = Icons.Rounded.Search,
        label = "Search"
    )
    object Favorite : BottomBarScreen(
        route = "favorite",
        icon = Icons.Rounded.FavoriteBorder,
        label = "Favorite"
    )
}