package com.lefarmico.moviesfinder.data.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.appEntity.CategoryDb
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.data.appEntity.MoviesByCategoryDb
import com.lefarmico.moviesfinder.providers.CategoryProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemDao {

    @Query("SELECT * FROM cached_item_header")
    fun getCachedItemHeaders(): List<ItemHeaderImpl>

    @Query("SELECT * FROM category_db WHERE category_name = :category")
    fun getCategoryDb(category: CategoryProvider.Category): CategoryDb

    @Query("SELECT * FROM cached_item_header WHERE movie_id = :movieId")
    fun getItemHeader(movieId: Int): ItemHeaderImpl

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
    fun getCategory(category: CategoryProvider.Category): Single<List<ItemHeaderImpl>>

    @Query("SELECT * FROM cached_item_header WHERE is_favorites = 1")
    fun getFavoritesMovies(): Observable<ItemHeaderImpl>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<ItemHeaderImpl>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieByCategory(moviesByCategoryDb: MoviesByCategoryDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryDb(categoryDb: CategoryDb)

    @Update(entity = ItemHeaderImpl::class)
    fun updateMovieDetails(item: ItemHeaderImpl)

    @Delete
    fun deleteMovie(movie: Movie)
}
