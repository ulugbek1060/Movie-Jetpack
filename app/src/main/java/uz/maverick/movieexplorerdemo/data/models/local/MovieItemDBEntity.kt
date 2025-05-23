package uz.maverick.movieexplorerdemo.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity

@Entity(tableName = "favorite_movies_table")
data class MovieItemDBEntity(
    @PrimaryKey
    val id: Int?,
    val poster_path: String?,
    val title: String?,
    val overview: String?,
    val vote_average: Double?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val original_language: String?,
    val original_title: String?,
    val popularity: Double?,
    val release_date: String?,
    val video: Boolean?,
    val vote_count: Int?
)

fun MovieItemDBEntity.toMovieItemEntity(): MovieItemEntity {
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