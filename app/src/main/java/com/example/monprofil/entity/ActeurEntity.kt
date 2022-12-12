package com.example.monprofil.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monprofil.models.TmdbActor
import com.example.monprofil.models.TmdbMovie

@Entity
data class ActeurEntity(val fiche: TmdbActor, @PrimaryKey val id: String)