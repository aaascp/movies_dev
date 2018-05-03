package br.com.aaascp.androidapp.di.module.infra

import android.app.Application
import android.arch.persistence.room.Room
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository
import br.com.aaascp.androidapp.infra.source.local.dao.movie.MovieLocalDataSource
import br.com.aaascp.androidapp.infra.repository.movie.MovieWithLocalDataRepository
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.RoomDatabase
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import dagger.Module
import dagger.Provides
import java.util.concurrent.*
import javax.inject.Singleton

@Module
open class DatabaseModule(application: Application) {

    private val database: RoomDatabase

    protected open fun name() = "app-db.db"

    init {
        database =
                Room.databaseBuilder(
                        application,
                        RoomDatabase::class.java,
                        name()
                ).fallbackToDestructiveMigration()
                        .build()
    }

    @Singleton
    @Provides
    fun providesDatabase(): AppDatabase {
        return database
    }

    @Singleton
    @Provides
    fun providesExecutor(): Executor {
        return ThreadPoolExecutor(
                2,
                2,
                0,
                TimeUnit.MILLISECONDS,
                LinkedBlockingQueue<Runnable>())
    }

    @Singleton
    @Provides
    fun providesMovieDataSource(database: AppDatabase): MovieLocalDataSource {
        return database.movieDao()
    }

    @Singleton
    @Provides
    fun movieRepository(
            db: AppDatabase,
            movieEndpoint: MovieEndpoint): MovieRepository {

        return MovieWithLocalDataRepository(
                db,
                movieEndpoint)
    }
}