package com.chainreaction.task.domain.usecase

import androidx.paging.PagingData
import com.chainreaction.task.domain.model.Movie
import com.chainreaction.task.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return movieRepository.getMovies()
    }
}