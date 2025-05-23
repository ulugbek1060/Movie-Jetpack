package uz.maverick.movieexplorerdemo.config

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.maverick.movieexplorerdemo.config.AppModule.BinderModule
import uz.maverick.movieexplorerdemo.data.repositories.MoviesRepositoryImpl
import uz.maverick.movieexplorerdemo.data.repositories.SavedMoviesRepositoryImpl
import uz.maverick.movieexplorerdemo.data.sources.local.AppDatabase
import uz.maverick.movieexplorerdemo.domain.repositories.MoviesRepository
import uz.maverick.movieexplorerdemo.domain.repositories.SavedMoviesRepository
import javax.inject.Singleton


@Module(includes = [BinderModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFileDao(db: AppDatabase) = db.getFileDao()

    private companion object {
        const val DATABASE_NAME = "movie_explorer_db"
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BinderModule {

        @Binds
        @Singleton
        fun bindMoviesRepo(repo: MoviesRepositoryImpl): MoviesRepository

        @Binds
        @Singleton
        fun bindSavedMoviesRepo(repo: SavedMoviesRepositoryImpl): SavedMoviesRepository
    }

}