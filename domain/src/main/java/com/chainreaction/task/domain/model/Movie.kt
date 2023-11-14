package com.chainreaction.task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Int? = null,
    val movieTitle: String? = null,
    val overview: String? = null,
    val imageId: String? = null
)
