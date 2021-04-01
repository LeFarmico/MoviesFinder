package com.lefarmico.moviesfinder.data

import android.content.ContentValues
import android.database.Cursor
import com.lefarmico.moviesfinder.data.db.DatabaseHelper
import com.lefarmico.moviesfinder.models.ItemHeader
import com.lefarmico.moviesfinder.models.MovieItem
import com.lefarmico.moviesfinder.models.MovieModel

class MainRepository(dbHelper: DatabaseHelper) {

    private val sqlDb = dbHelper.readableDatabase

    private lateinit var cursor: Cursor

    fun putToDb(item: ItemHeader) {
        val cv = ContentValues()
        cv.apply {
            put(DatabaseHelper.COLUMN_TITLE, item.title)
            put(DatabaseHelper.COLUMN_DESCRIPTION, item.description)
            put(DatabaseHelper.COLUMN_POSTER, item.posterPath)
            put(DatabaseHelper.COLUMN_RATING, item.rating)
            put(DatabaseHelper.COLUMN_MOVIE_ID, item.id)
            put(DatabaseHelper.COLUMN_REALISE_DATE, item.releaseDate)
        }
        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }

    fun getFromDB(): List<MovieItem> {
        cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)

        val result = mutableListOf<MovieItem>()

        if (cursor.moveToFirst()) {
            do {
                val movie = MovieModel(
                    title = cursor.getString(1),
                    posterPath = cursor.getString(3),
                    rating = cursor.getDouble(5),
                    description = cursor.getString(4),
                    id = cursor.getInt(2),
                    releaseDate = cursor.getString(6),
                    isFavorite = false,
                    yourRate = 0,
                    genres = listOf(),
                    actors = listOf(),
                    directors = listOf(),
                    watchProviders = listOf(),
                    length = 0,
                    photosPath = listOf()
                )
                result.add(movie)
            } while (cursor.moveToNext())
        }
        return result
    }
}
