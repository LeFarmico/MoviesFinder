package com.lefarmico.moviesfinder.data.entity

import android.os.Parcelable
import androidx.room.*
import com.lefarmico.moviesfinder.utils.CastTypeConverter
import com.lefarmico.moviesfinder.utils.ListOfStringsTypeConverters
import com.lefarmico.moviesfinder.utils.ProviderTypeConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "saved_movie", indices = [Index(value = ["movie_id"], unique = true)])
@TypeConverters(
    ListOfStringsTypeConverters::class,
    ProviderTypeConverter::class,
    CastTypeConverter::class
)
@Parcelize
data class MovieData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movie_id") val itemId: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_watchlist") val isWatchlist: Boolean,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "your_rate") val yourRate: Int,
    @ColumnInfo(name = "genres") val genres: List<String>,
    @ColumnInfo(name = "actors") val actors: List<MovieCastData>,
    @ColumnInfo(name = "directors") val directors: List<MovieCastData>,
    @ColumnInfo(name = "watch_providers") val watchMovieProviderData: List<MovieProviderData> = listOf(),
    @ColumnInfo(name = "length") val length: Int,
    @ColumnInfo(name = "photos_path") val photosPath: List<String>,
    @ColumnInfo(name = "release_date") val releaseDate: String
) : Parcelable {
    fun toItemHeaderImpl(): MovieBriefData =
        MovieBriefData(
            id = this.id,
            itemId = this.itemId,
            posterPath = this.posterPath,
            title = this.title,
            rating = this.rating,
            description = this.description,
            isWatchlist = this.isWatchlist,
            yourRate = this.yourRate,
            releaseDate = this.releaseDate
        )
}
