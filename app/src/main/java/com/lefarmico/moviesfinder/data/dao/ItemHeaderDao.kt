package com.lefarmico.moviesfinder.data.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.appEntity.*

@Dao
interface ItemHeaderDao {

    @Query("SELECT * FROM cached_item_header")
    fun getCachedItemHeaders(): List<ItemHeaderImpl>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ItemHeaderImpl>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCast(cast: Cast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genresDb: GenresDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProvider(provider: Provider)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(photosDb: PhotosDb)

    @Update(entity = ItemHeaderImpl::class)
    fun updateMovieDetails(item: ItemHeaderImpl)
}
