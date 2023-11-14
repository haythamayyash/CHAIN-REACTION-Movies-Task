package com.chainreaction.task.data.di

import android.app.Application
import androidx.room.Room
import com.chainreaction.task.data.db.MovieDB
import com.chainreaction.task.data.db.MovieDao
import com.chainreaction.task.data.db.MovieRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME_MOVIE = "movie_db"
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): MovieDB =
        Room.databaseBuilder(app, MovieDB::class.java, DATABASE_NAME_MOVIE).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(movieDB: MovieDB): MovieDao = movieDB.movieDao()

    @Singleton
    @Provides
    fun provideMovieRemoteKeysDao(movieDB: MovieDB): MovieRemoteKeysDao =
        movieDB.movieRemoteKeysDao()


}