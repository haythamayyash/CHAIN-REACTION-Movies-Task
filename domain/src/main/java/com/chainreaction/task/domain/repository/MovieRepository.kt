package com.chainreaction.task.domain.repository

import androidx.paging.PagingData
import com.chainreaction.task.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}