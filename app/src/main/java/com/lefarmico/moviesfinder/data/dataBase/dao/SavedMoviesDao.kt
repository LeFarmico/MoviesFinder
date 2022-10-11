package com.lefarmico.moviesfinder.data.dataBase.dao

import androidx.room.*
import com.lefarmico.moviesfinder.data.entity.MovieDetailedData

@Dao
interface SavedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetailed(movieDetailedData: MovieDetailedData): Long

    @Delete
    suspend fun deleteMovieDetails(movieDetailedData: MovieDetailedData): Int

    @Query("SELECT * FROM saved_movie WHERE movie_id = :movieId")
    suspend fun getMovieDetailed(movieId: Int): MovieDetailedData?

    @Query("SELECT * FROM saved_movie WHERE is_watchlist = 1")
    suspend fun getWatchListMovieDetailed(): List<MovieDetailedData>
}
