package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotosDb(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "photo_path")val photoPath: String,
    @ColumnInfo(name = "movie_details_id") val movieDetailsId: Int = 0
)
