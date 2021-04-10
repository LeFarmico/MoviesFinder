
package com.lefarmico.moviesfinder.data.entity.appEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cached_item_header", indices = [Index(value = ["title"], unique = false)])
data class ItemHeaderImpl(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "movie_id") override val id: Int,
    @ColumnInfo(name = "poster_path") override val posterPath: String = "",
    @ColumnInfo(name = "title") override val title: String = "",
    @ColumnInfo(name = "rating") override val rating: Double = 0.0,
    @ColumnInfo(name = "overview") override val description: String = "",
    override var isFavorite: Boolean = false,
    @ColumnInfo(name = "your_rate") override val yourRate: Int = 0,
    @ColumnInfo(name = "release_date") override val releaseDate: String
) : ItemHeader
