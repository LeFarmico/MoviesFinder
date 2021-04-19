
package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.*

@Entity(tableName = "cached_item_header", indices = [Index(value = ["movie_id"], unique = true)])
data class ItemHeaderImpl(
    @ColumnInfo(name = "id") override val id: Int = 0,
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "movie_id") override val itemId: Int,
    @ColumnInfo(name = "poster_path") override val posterPath: String? = "",
    @ColumnInfo(name = "title") override val title: String = "",
    @ColumnInfo(name = "rating") override val rating: Double = 0.0,
    @ColumnInfo(name = "overview") override val description: String = "",
    @ColumnInfo(name = "is_favorites") override var isFavorite: Boolean = false,
    @ColumnInfo(name = "your_rate") override val yourRate: Int = 0,
    @ColumnInfo(name = "release_date") override val releaseDate: String
) : ItemHeader