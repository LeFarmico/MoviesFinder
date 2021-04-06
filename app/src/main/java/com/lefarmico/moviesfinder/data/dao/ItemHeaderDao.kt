package com.lefarmico.moviesfinder.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lefarmico.moviesfinder.models.ItemHeaderModel

@Dao
interface ItemHeaderDao {

    @Query("SELECT * FROM cached_item_header")
    fun getCachedItemHeaders(): List<ItemHeaderModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ItemHeaderModel>)
}
