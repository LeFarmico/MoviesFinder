
package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.*

@Entity(tableName = "cached_item_header", indices = [Index(value = ["movie_id"], unique = true)])
data class Header(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Int = 0,
    @ColumnInfo(name = "movie_id") override val itemId: Int = 0,
    @ColumnInfo(name = "poster_path") override val posterPath: String? = "",
    @ColumnInfo(name = "title") override val title: String = "",
    @ColumnInfo(name = "rating") override val rating: Double = 0.0,
    @ColumnInfo(name = "overview") override val description: String = "",
    @ColumnInfo(name = "is_favorites") override var isWatchlist: Boolean = false,
    @ColumnInfo(name = "your_rate") override val yourRate: Int = 0,
    @ColumnInfo(name = "release_date") override val releaseDate: String = "00.00.0000"
) : ItemHeader
