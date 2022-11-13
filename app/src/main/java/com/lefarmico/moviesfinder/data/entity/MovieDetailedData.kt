package com.lefarmico.moviesfinder.data.entity

import android.os.Parcelable
import androidx.room.*
import com.lefarmico.moviesfinder.utils.mapper.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "saved_movie", indices = [Index(value = ["movie_id"], unique = true)])
@TypeConverters(
    ListOfStringsTypeConverters::class,
    ProviderTypeConverter::class,
    CastTypeConverter::class,
    CrewTypeConverter::class,
    MovieBriefDataConverter::class
)
@Parcelize
data class MovieDetailedData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_watchlist") val isWatchlist: Boolean,
    @ColumnInfo(name = "rating") val rating: Float,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "your_rate") val yourRate: Int?,
    @ColumnInfo(name = "genres") val genres: List<String>,
    @ColumnInfo(name = "actors") val actors: List<MovieCastData>?,
    @ColumnInfo(name = "directors") val directors: List<MovieCrewData>?,
    @ColumnInfo(name = "watch_providers") val watchMovieProviderData: List<MovieProviderData>?,
    @ColumnInfo(name = "length") val length: Int,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "recommendations") val recommendations: List<MovieBriefData>?
) : Parcelable
