package com.lefarmico.moviesfinder.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lefarmico.moviesfinder.data.entity.appEntity.ItemHeaderImpl

@Dao
interface ItemHeaderDao {

    @Query("SELECT * FROM cached_item_header")
    fun getCachedItemHeaders(): List<ItemHeaderImpl>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ItemHeaderImpl>)
}
