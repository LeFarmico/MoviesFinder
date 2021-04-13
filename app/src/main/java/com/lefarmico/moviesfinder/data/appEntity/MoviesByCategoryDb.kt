package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.*
import com.lefarmico.moviesfinder.providers.CategoryProvider

// TODO : Правильно свзязатя
@Entity(
    tableName = "movies_by_category",
    indices = [Index("movie_id", "category_type", unique = true)],
)
data class MoviesByCategoryDb(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "category_type") val categoryType: CategoryProvider.Category,
)
