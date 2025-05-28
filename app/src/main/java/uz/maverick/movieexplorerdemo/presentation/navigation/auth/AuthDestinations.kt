package uz.maverick.movieexplorerdemo.presentation.navigation.auth

sealed class AuthDestinations(val route: String) {
    object RegisterScreen : AuthDestinations("register_screen")
}