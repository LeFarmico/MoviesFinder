package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDb(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "genres_key") val genresKey: Int,
    @ColumnInfo(name = "actors_key") val actorsKey: Int,
    @ColumnInfo(name = "directors_key") val directorsKey: Int,
    @ColumnInfo(name = "providers_key") val providersKey: Int,
    @ColumnInfo(name = "length") val length: Int,
    @ColumnInfo(name = "photos_key") val photosKey: Int,
)
