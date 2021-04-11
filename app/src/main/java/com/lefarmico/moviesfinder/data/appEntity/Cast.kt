package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast")
data class Cast(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "person_id") val personId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "profile_path") val profilePath: String? = "",
    @ColumnInfo(name = "character") val character: String = "",
    @ColumnInfo(name = "poster_path") val posterPath: String = "",
    @ColumnInfo(name = "movie_details_id") val movieDetailsId: Int = 0
)
