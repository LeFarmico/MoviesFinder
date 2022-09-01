package com.lefarmico.moviesfinder.data.entity

import androidx.room.*
import com.lefarmico.moviesfinder.providers.CategoryProvider

@Entity(
    tableName = "movie_id_by_category",
    indices = [Index("movie_id", "category_type", unique = true)],
)
data class MovieIdByCategoryData(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "category_type") val categoryType: CategoryProvider.Category,
)
