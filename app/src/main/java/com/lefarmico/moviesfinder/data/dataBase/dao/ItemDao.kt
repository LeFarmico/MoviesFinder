package com.lefarmico.moviesfinder.data.dataBase.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM cached_movie_brief")
    fun getCachedItemHeaders(): Flow<List<MovieBriefData>>

    @Query("SELECT * FROM cached_movie_brief WHERE movie_id = :movieId")
    fun getItemHeader(movieId: Int): MovieBriefData

//    @Query(
//        "SELECT " +
//            "movie.id, " +
//            "movie.movie_id, " +
//            "movie.poster_path,  " +
//            "movie.title, " +
//            "movie.rating, " +
//            "movie.overview, " +
//            "movie.is_favorites, " +
//            "movie.your_rate, " +
//            "movie.release_date " +
//            "FROM cached_movie_brief movie " +
//            "INNER JOIN movie_id_by_category mbc ON mbc.movie_id = movie.movie_id " +
//            "WHERE mbc.category_type LIKE :categoryData " +
//            "ORDER BY movie.title "
//    )
//    fun getCategory(categoryData: CategoryData): Flow<List<MovieBriefData>>

    @Query("SELECT * FROM cached_movie_brief WHERE is_favorites = 1")
    fun getFavoritesMovies(): Flow<List<MovieBriefData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<MovieBriefData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetailed(movieDetailedData: MovieDetailedData): Long

    @Update(entity = MovieBriefData::class)
    fun updateMovieDetails(item: MovieBriefData)

    @Delete
    fun deleteMovie(movieDetailedData: MovieDetailedData): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putSearchHeader(searchRequestData: SearchRequestData)

    @Query("SELECT text_request FROM search_request")
    fun getLastSearchRequests(): Flow<List<String>>
}
