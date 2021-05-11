package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.*
import com.lefarmico.moviesfinder.utils.CastTypeConverter
import com.lefarmico.moviesfinder.utils.ListOfStringsTypeConverters
import com.lefarmico.moviesfinder.utils.ProviderTypeConverter

@Entity(tableName = "movie", indices = [Index(value = ["movie_id"], unique = true)])
@TypeConverters(
    ListOfStringsTypeConverters::class,
    ProviderTypeConverter::class,
    CastTypeConverter::class
)
data class Movie(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Int = 0,
    @ColumnInfo(name = "movie_id") override val itemId: Int,
    @ColumnInfo(name = "poster_path") override val posterPath: String,
    @ColumnInfo(name = "title") override val title: String,
    @ColumnInfo(name = "is_watchlist") override var isWatchlist: Boolean,
    @ColumnInfo(name = "rating") override val rating: Double,
    @ColumnInfo(name = "description") override val description: String,
    @ColumnInfo(name = "your_rate") override val yourRate: Int,
    @ColumnInfo(name = "genres") override val genres: List<String>,
    @ColumnInfo(name = "actors") override val actors: List<Cast>,
    @ColumnInfo(name = "directors") override val directors: List<Cast>,
    @ColumnInfo(name = "watch_providers") override val watchProviders: List<Provider> = listOf(),
    @ColumnInfo(name = "length") override val length: Int,
    @ColumnInfo(name = "photos_path") override val photosPath: List<String>,
    @ColumnInfo(name = "release_date") override val releaseDate: String
) : MovieItem
