package com.chainreaction.task.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.chainreaction.task.data.api.MovieApi
import com.chainreaction.task.data.db.MovieDB
import com.chainreaction.task.data.remote.mapper.toDomain
import com.chainreaction.task.domain.model.Movie
import com.chainreaction.task.domain.model.MovieRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(private val movieApi: MovieApi, private val movieDB: MovieDB) :
    RemoteMediator<Int, Movie>() {

    private val movieDao = movieDB.movieDao()
    private val movieRemoteKeysDao = movieDB.movieRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }

                else -> {
                    null
                }
            }


            var endOfPaginationReached: Boolean
            kotlin.runCatching {
                movieApi.getMovies(page = page!!)
            }.onSuccess { moviesResponse ->
                endOfPaginationReached = moviesResponse == null
                moviesResponse?.let {
                    movieDB.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            movieDao.deleteAllMovies()
                            movieRemoteKeysDao.deleteAllMovieRemoteKeys()
                        }
                        var prevPage: Int?
                        var nextPage: Int?

                        moviesResponse.page.let { pageNumber ->
                            nextPage = pageNumber?.let { pageNumber + 1 }
                            prevPage =
                                pageNumber?.let { if (pageNumber <= 1) null else pageNumber - 1 }
                        }

                        val keys = moviesResponse.movies?.map { movie ->
                            MovieRemoteKeys(
                                id = movie.id!!,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        keys?.let { movieKeys ->
                            movieRemoteKeysDao.addAllMovieRemoteKeys(
                                movieRemoteKeys = movieKeys
                            )
                        }
                        moviesResponse.movies?.toDomain()
                            ?.let { movies -> movieDao.addMovies(movies = movies) }
                    }
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

            }.onFailure {
                return MediatorResult.Error(it)
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
        return MediatorResult.Error(Throwable())
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>,
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.getMovieRemoteKeys(movieId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Movie>,
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movie.id?.let { movieRemoteKeysDao.getMovieRemoteKeys(movieId = it) }
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Movie>,
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movie.id?.let { movieRemoteKeysDao.getMovieRemoteKeys(movieId = it) }
            }
    }
}