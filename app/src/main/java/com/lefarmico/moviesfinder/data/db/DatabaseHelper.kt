package com.lefarmico.moviesfinder.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE TEXT UNIQUE," +
                "$COLUMN_MOVIE_ID INTEGER UNIQUE," +
                "$COLUMN_POSTER TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_RATING REAL," +
                "$COLUMN_REALISE_DATE TEXT);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        // Название самой БД
        private const val DATABASE_NAME = "films_list.db"
        // Версия БД
        private const val DATABASE_VERSION = 1

        // Константы для работы с таблицей, они на понадобятся в CRUD операциях и,
        // возможно, в составлении запросов
        const val TABLE_NAME = "films_table"
        const val COLUMN_ID = "id"

        const val COLUMN_TITLE = "title"
        const val COLUMN_POSTER = "poster_path"
        const val COLUMN_DESCRIPTION = "overview"
        const val COLUMN_RATING = "vote_average"
        const val COLUMN_MOVIE_ID = "movie_id"
        const val COLUMN_REALISE_DATE = "realise_date"
    }
}
