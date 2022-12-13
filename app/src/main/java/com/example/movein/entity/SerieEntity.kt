package com.example.movein.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movein.models.TmdbSerie

@Entity
data class SerieEntity(val content: TmdbSerie, @PrimaryKey val id: String)