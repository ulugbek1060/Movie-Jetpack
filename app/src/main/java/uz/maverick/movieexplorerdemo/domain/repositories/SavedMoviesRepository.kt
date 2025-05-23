package uz.maverick.movieexplorerdemo.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity

interface SavedMoviesRepository {
    suspend fun addToFavorites(movie: MovieItemEntity)
    fun getFavoriteMovies(): Flow<List<MovieItemEntity>>
    fun isMovieInFavorites(movieId: Int): Flow<Boolean>
    suspend fun removeFromFavorites(movieId: Int)
    suspend fun clearAllFavorites()
}