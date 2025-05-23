package uz.maverick.movieexplorerdemo.domain.entities

data class MovieDetailEntity(
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int?,
    val genres: List<Any?>?,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun MovieDetailEntity.toMovieItemEntity(): MovieItemEntity {
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
