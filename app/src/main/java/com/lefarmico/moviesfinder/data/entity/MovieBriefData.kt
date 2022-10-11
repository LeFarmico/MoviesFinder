
package com.lefarmico.moviesfinder.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cached_movie_brief", indices = [Index(value = ["movie_id"], unique = true)])
data class MovieBriefData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "rating") val rating: Float? = null,
    @ColumnInfo(name = "overview") val description: String,
    @ColumnInfo(name = "is_favorites") val isWatchlist: Boolean = false,
    @ColumnInfo(name = "your_rate") val yourRate: Int? = null,
    @ColumnInfo(name = "release_date") val releaseDate: String
) : Parcelable
