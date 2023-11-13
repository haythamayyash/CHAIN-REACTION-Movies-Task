package com.chainreaction.task.domain.model

data class MoviesResult(
    val page: Int? = null,
    val totalPages: Int? = null,
    val movieList: List<Movie?>? = null
)