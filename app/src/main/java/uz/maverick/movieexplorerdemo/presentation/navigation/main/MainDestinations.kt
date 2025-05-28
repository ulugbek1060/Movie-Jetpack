package uz.maverick.movieexplorerdemo.presentation.navigation.main



sealed class MainDestinations(val route: String) {
    object MainScreen : MainDestinations("main_screen")
    object MovieDetailsScreen : MainDestinations("movie_details_screen/{movieId}") {
        fun createRoute(movieId: Int) = "movie_details_screen/$movieId"
    }
}