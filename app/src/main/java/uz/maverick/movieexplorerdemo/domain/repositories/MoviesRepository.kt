package uz.maverick.movieexplorerdemo.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.maverick.movieexplorerdemo.domain.entities.MovieDetailEntity
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity

interface MoviesRepository {
    fun getPopularMovies(): Flow<PagingData<MovieItemEntity>>
    fun searchMovies(query: String):Flow<PagingData<MovieItemEntity>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetailEntity>
}