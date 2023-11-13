package com.chainreaction.task.domain.repository

import androidx.paging.PagingData
import com.chainreaction.task.domain.model.Movie
import com.chainreaction.task.domain.model.MoviesResult
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getMovies(
        page: Int,
        includeAdult: Boolean,
        includeVideo: Boolean
    ): Flow<PagingData<Movie>>
}