package com.lefarmico.moviesfinder.data.dataBase.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.entity.*
import com.lefarmico.moviesfinder.ui.common.provider.CategoryProvider
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemDao {

    @Query("SELECT * FROM cached_movie_brief")
    fun getCachedItemHeaders(): List<MovieBriefData>

    @Query("SELECT * FROM cached_movie_brief WHERE movie_id = :movieId")
    fun getItemHeader(movieId: Int): MovieBriefData

    @Query(
        "SELECT " +
            "movie.id, " +
            "movie.movie_id, " +
            "movie.poster_path,  " +
            "movie.title, " +
            "movie.rating, " +
            "movie.overview, " +
            "movie.is_favorites, " +
            "movie.your_rate, " +
            "movie.release_date " +
            "FROM cached_movie_brief movie " +
            "INNER JOIN movie_id_by_category mbc ON mbc.movie_id = movie.movie_id " +
            "WHERE mbc.category_type LIKE :category " +
            "ORDER BY movie.title "
    )
    fun getCategory(category: CategoryProvider.Category): Single<List<MovieBriefData>>

    @Query("SELECT * FROM cached_movie_brief WHERE is_favorites = 1")
    fun getFavoritesMovies(): Single<List<MovieBriefData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<MovieBriefData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieData: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieByCategory(movieIdByCategoryData: MovieIdByCategoryData)

    @Update(entity = MovieBriefData::class)
    fun updateMovieDetails(item: MovieBriefData)

    @Delete
    fun deleteMovie(movieData: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putSearchHeader(searchRequestData: SearchRequestData)

    @Query("SELECT text_request FROM search_request")
    fun getLastSearchRequests(): Single<List<String>>
}
