package com.chainreaction.task.data.repository

import androidx.paging.PagingData
import com.chainreaction.task.data.repository.datasource.MovieRemoteDataSource
import com.chainreaction.task.domain.model.Movie
import com.chainreaction.task.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {
    override fun getMovies(): Flow<PagingData<Movie>> {
        return movieRemoteDataSource.getMovies()
    }
}