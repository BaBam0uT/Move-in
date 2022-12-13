package com.example.movein.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movein.models.TmdbActor

@Entity
data class ActeurEntity(val fiche: TmdbActor, @PrimaryKey val id: String)