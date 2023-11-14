package com.chainreaction.task.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chainreaction.task.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<Movie>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}