package uz.maverick.movieexplorerdemo.domain.entities

data class MovieItemContainer(
    val movie: MovieItemEntity,
    val isInFavorites: Boolean
)