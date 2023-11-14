package com.chainreaction.task.data.api

import com.chainreaction.task.data.remote.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/discover/movie?language=en-US")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
    ): MoviesResponse?


}