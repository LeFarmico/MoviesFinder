package com.lefarmico.moviesfinder.data.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.providers.CategoryProvider
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemDao {

    @Query("SELECT * FROM cached_item_header")
    fun getCachedItemHeaders(): List<Header>

    @Query("SELECT * FROM category_db WHERE category_name = :category")
    fun getCategoryDb(category: CategoryProvider.Category): CategoryDb

    @Query("SELECT * FROM cached_item_header WHERE movie_id = :movieId")
    fun getItemHeader(movieId: Int): Header

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
            "FROM cached_item_header movie " +
            "INNER JOIN movies_by_category mbc ON mbc.movie_id = movie.movie_id " +
            "WHERE mbc.category_type LIKE :category " +
            "ORDER BY movie.title "
    )
    fun getCategory(category: CategoryProvider.Category): Single<List<Header>>

    @Query("SELECT * FROM cached_item_header WHERE is_favorites = 1")
    fun getFavoritesMovies(): Single<List<Header>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<Header>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieByCategory(moviesByCategoryDb: MoviesByCategoryDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryDb(categoryDb: CategoryDb)

    @Update(entity = Header::class)
    fun updateMovieDetails(item: Header)

    @Delete
    fun deleteMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putSearchHeader(searchRequestDb: SearchRequestDb)

    @Query("SELECT text_request FROM search_request")
    fun getLastSearchRequests(): Single<List<String>>
}
