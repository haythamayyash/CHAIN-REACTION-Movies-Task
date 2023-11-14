package com.chainreaction.task.data.di

import com.chainreaction.task.data.api.MovieApi
import com.chainreaction.task.data.db.MovieDB
import com.chainreaction.task.data.repository.MovieRepositoryImpl
import com.chainreaction.task.data.repository.datasource.MovieRemoteDataSource
import com.chainreaction.task.data.repository.datasource.MovieRemoteDataSourceImpl
import com.chainreaction.task.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(
        movieApi: MovieApi,
        movieDB: MovieDB,
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(movieApi, movieDB)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource)
    }
}