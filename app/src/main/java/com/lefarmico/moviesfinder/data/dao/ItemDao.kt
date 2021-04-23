package com.lefarmico.moviesfinder.data.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.appEntity.CategoryDb
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import com.lefarmico.moviesfinder.data.appEntity.Movie
import com.lefarmico.moviesfinder.data.appEntity.MoviesByCategoryDb
import com.lefarmico.moviesfinder.providers.CategoryProvider
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM cached_item_header")
    suspend fun getCachedItemHeaders(): List<ItemHeaderImpl>

    @Query("SELECT * FROM category_db WHERE category_name = :category")
    suspend fun getCategoryDb(category: CategoryProvider.Category): CategoryDb

    @Query("SELECT * FROM cached_item_header WHERE movie_id = :movieId")
    suspend fun getItemHeader(movieId: Int): ItemHeaderImpl

    // TODO Спросить про LIveData с Room
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
    fun getCategory(category: CategoryProvider.Category): Flow<List<ItemHeaderImpl>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ItemHeaderImpl>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieByCategory(moviesByCategoryDb: MoviesByCategoryDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryDb(categoryDb: CategoryDb)

    @Update(entity = ItemHeaderImpl::class)
    suspend fun updateMovieDetails(item: ItemHeaderImpl)
}
