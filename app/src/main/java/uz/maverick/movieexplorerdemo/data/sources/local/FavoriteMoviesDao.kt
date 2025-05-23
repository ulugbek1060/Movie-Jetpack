package uz.maverick.movieexplorerdemo.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.maverick.movieexplorerdemo.data.models.local.MovieItemDBEntity

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: MovieItemDBEntity)

    @Query("SELECT * FROM favorite_movies_table")
    fun getAllFavorites(): Flow<List<MovieItemDBEntity>>

    @Query("DELETE FROM favorite_movies_table WHERE id = :movieId")
    suspend fun deleteFavorite(movieId: Int)

    @Query("SELECT EXISTS(SELECT * FROM favorite_movies_table WHERE id = :movieId)")
    suspend fun isFavorite(movieId: Int): Boolean

    @Query("SELECT COUNT(*) FROM favorite_movies_table")
    suspend fun getCount(): Int

    @Query("DELETE FROM favorite_movies_table")
    suspend fun deleteAllFavorites()
}