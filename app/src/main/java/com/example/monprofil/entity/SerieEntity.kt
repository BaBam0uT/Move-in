package com.example.monprofil.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monprofil.models.TmdbMovie
import com.example.monprofil.models.TmdbSerie

@Entity
data class SerieEntity(val fiche: TmdbSerie, @PrimaryKey val id: String)