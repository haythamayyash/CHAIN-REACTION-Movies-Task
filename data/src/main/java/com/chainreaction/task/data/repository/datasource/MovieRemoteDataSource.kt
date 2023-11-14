package com.chainreaction.task.data.repository.datasource

import androidx.paging.PagingData
import com.chainreaction.task.data.remote.model.MoviesResponse
import com.chainreaction.task.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
     fun getMovies(): Flow<PagingData<Movie>>
}