package com.example.movein.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movein.models.TmdbMovie

@Entity
data class MovieEntity(val content: TmdbMovie, @PrimaryKey val id: String)