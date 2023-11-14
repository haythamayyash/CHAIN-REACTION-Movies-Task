package com.chainreaction.task.data.remote.mapper

import com.chainreaction.task.data.remote.model.MoviesResponse
import com.chainreaction.task.domain.model.Movie

fun MoviesResponse.Movie.toDomain() = Movie(
    id = this.id,
    movieTitle = this.title,
    overview = this.overview,
    imageId = this.posterPath
)

fun List<MoviesResponse.Movie>?.toDomain() : List<Movie>?{
    return this?.map { movieResponse ->
        movieResponse.toDomain()
    }
}
