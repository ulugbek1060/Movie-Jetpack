package uz.maverick.movieexplorerdemo.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uz.maverick.movieexplorerdemo.data.models.local.toMovieItemEntity
import uz.maverick.movieexplorerdemo.data.sources.local.FavoriteMoviesDao
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.domain.entities.toMovieItemDBEntity
import uz.maverick.movieexplorerdemo.domain.repositories.SavedMoviesRepository
import javax.inject.Inject

class SavedMoviesRepositoryImpl @Inject constructor(
    private val favoriteMoviesDao: FavoriteMoviesDao,
) : SavedMoviesRepository {

    override suspend fun addToFavorites(
        movie: MovieItemEntity
    ) = withContext(Dispatchers.IO) {
        favoriteMoviesDao.insertFavorite(movie.toMovieItemDBEntity())
    }

    override fun getFavoriteMovies(): Flow<List<MovieItemEntity>> =
        favoriteMoviesDao.getAllFavorites().map { list ->
            list.map { it.toMovieItemEntity() }
        }.flowOn(Dispatchers.IO)

    override fun isMovieInFavorites(movieId: Int) =
        favoriteMoviesDao.isFavorite(movieId)
            .flowOn(Dispatchers.IO)

    override suspend fun removeFromFavorites(
        movieId: Int
    ) = withContext(Dispatchers.IO) {
        favoriteMoviesDao.deleteFavorite(movieId)
    }

    override suspend fun clearAllFavorites() = withContext(Dispatchers.IO) {
        favoriteMoviesDao.deleteAllFavorites()
    }

}