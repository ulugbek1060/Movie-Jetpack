package uz.maverick.movieexplorerdemo.data.sources.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.maverick.movieexplorerdemo.BuildConfig
import uz.maverick.movieexplorerdemo.data.models.remote.MovieDetailData
import uz.maverick.movieexplorerdemo.data.models.remote.MoviesData


/**
 * API KEY = "7f442ff583bfb38f84caafd113cbccc0"
 */
interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int = 1,
    ): MoviesData

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") lang: String = "ru-RU"
    ): MoviesData

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
    ): MovieDetailData

}