package uz.maverick.movieexplorerdemo.presentation.navigation

sealed class Destinations(val label: String) {
    object HomeScreen : Destinations("home_screen")
    object DetailScreen : Destinations("detail_screen/{id}"){
        fun passId(id: Int): String {
            return "detail_screen/$id"
        }
    }
    object SearchScreen : Destinations("search_screen")
    object FavoriteScreen : Destinations("favorite_screen")
}