package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.lefarmico.moviesfinder.providers.CategoryProvider

@Entity(tableName = "category_db", indices = [Index("category_title_resource", "category_name", unique = true)])
data class CategoryDb(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "category_name") val categoryName: CategoryProvider.Category,
    @ColumnInfo(name = "category_title_resource") val categoryTitleResource: Int
)
