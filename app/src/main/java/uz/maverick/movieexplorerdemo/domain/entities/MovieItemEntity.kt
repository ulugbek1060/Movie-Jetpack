package uz.maverick.movieexplorerdemo.domain.entities

import uz.maverick.movieexplorerdemo.data.models.local.MovieItemDBEntity
import uz.maverick.movieexplorerdemo.data.models.remote.ResultData

data class MovieItemEntity(
    val adult: Boolean?,
    val backdrop_path: String?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun ResultData.toMovieItemEntity(): MovieItemEntity {
    return MovieItemEntity(
        id = id,
        poster_path = poster_path,
        title = title,
        overview = overview,
        vote_average = vote_average,
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_title = original_title,
        popularity = popularity,
        release_date = release_date,
        video = video,
        vote_count = vote_count
    )
}

fun MovieItemEntity.toMovieItemDBEntity(): MovieItemDBEntity {
    return MovieItemDBEntity(
        id = id,
        poster_path = poster_path,
        title = title,
        overview = overview,
        vote_average = vote_average,
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_title = original_title,
        popularity = popularity,
        release_date = release_date,
        video = video,
        vote_count = vote_count
    )
}