package uz.maverick.movieexplorerdemo.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.maverick.movieexplorerdemo.data.models.remote.toMovieDetailEntity
import uz.maverick.movieexplorerdemo.data.sources.paging.PopularMoviesPagingSource
import uz.maverick.movieexplorerdemo.data.sources.paging.SearchMoviesPagingSource
import uz.maverick.movieexplorerdemo.data.sources.remote.MovieApi
import uz.maverick.movieexplorerdemo.domain.entities.MovieDetailEntity
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.domain.entities.toMovieItemEntity
import uz.maverick.movieexplorerdemo.domain.repositories.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MovieApi,
) : MoviesRepository {

    override fun getPopularMovies(): Flow<PagingData<MovieItemEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PopularMoviesPagingSource(api) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovieItemEntity()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun searchMovies(
        query: String
    ): Flow<PagingData<MovieItemEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchMoviesPagingSource(api, query) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovieItemEntity()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailEntity> = flow {
        emit(api.getMovieDetails(movieId = movieId))
    }.map {
        it.toMovieDetailEntity()
    }.flowOn(Dispatchers.IO)
}