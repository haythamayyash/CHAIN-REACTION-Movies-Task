package com.chainreaction.task.domain.model

import androidx.room.Entity

@Entity(tableName = "movies")
data class Movie(
    val id: Int? = null,
    val movieTitle: String? = null,
    val overview: String? = null,
    val imageId: String? = null
)
