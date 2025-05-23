package uz.maverick.movieexplorerdemo.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.maverick.movieexplorerdemo.data.models.local.MovieItemDBEntity

@Database(
    entities = [MovieItemDBEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFileDao(): FavoriteMoviesDao
}