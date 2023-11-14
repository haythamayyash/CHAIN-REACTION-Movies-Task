package com.chainreaction.task.data.repository.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chainreaction.task.data.api.MovieApi
import com.chainreaction.task.data.db.MovieDB
import com.chainreaction.task.data.paging.MovieRemoteMediator
import com.chainreaction.task.data.remote.model.MoviesResponse
import com.chainreaction.task.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDB: MovieDB
) : MovieRemoteDataSource {
    private val movieDao = movieDB.movieDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { movieDao.getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(
                movieApi,
                movieDB
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }
}