package com.lefarmico.moviesfinder.data.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.appEntity.*
import com.lefarmico.moviesfinder.providers.CategoryProvider
import io.reactivex.rxjava3.core.Observable
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
            "cached_item_header.id, " +
            "cached_item_header.movie_id, " +
            "cached_item_header.poster_path,  " +
            "cached_item_header.title, " +
            "cached_item_header.rating, " +
            "cached_item_header.overview, " +
            "cached_item_header.is_favorites, " +
            "cached_item_header.your_rate, " +
            "cached_item_header.release_date " +
            "FROM cached_item_header " +
            "INNER JOIN movies_by_category ON movies_by_category.movie_id = cached_item_header.movie_id " +
            "WHERE movies_by_category.category_type LIKE :category"
    )
    fun getCategory(category: CategoryProvider.Category): Single<List<Header>>

    @Query("SELECT * FROM cached_item_header WHERE is_favorites = 1")
    fun getFavoritesMovies(): Observable<Header>

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
    fun putSearchRequest(searchRequestDb: SearchRequestDb)

    @Query("SELECT text_request FROM search_request LIMIT 6")
    fun getLastSearchRequests(): Single<List<String>>
}
