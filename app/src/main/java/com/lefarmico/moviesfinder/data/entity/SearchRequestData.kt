package com.lefarmico.moviesfinder.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "search_request", indices = [Index("text_request", unique = true)])
data class SearchRequestData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "text_request") val textRequest: String
)
