package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.dataBase.dao.ItemDao
import com.lefarmico.moviesfinder.data.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val itemDao: ItemDao) {

    fun putItemHeadersToDb(movieBriefDataList: List<MovieBriefData>) = itemDao.insertAll(movieBriefDataList)

    fun putMovieToDb(movieDetailedData: MovieDetailedData) = itemDao.insertMovie(movieDetailedData)

//    fun getCategoryFromDB(categoryData: CategoryData): Flow<List<MovieBriefData>> =
//        itemDao.getCategory(categoryData)

    fun updateItemHeader(movieBriefData: MovieBriefData) {
        itemDao.updateMovieDetails(movieBriefData)
    }

    fun deleteMovieFromDB(movieDetailedData: MovieDetailedData) {
        itemDao.deleteMovie(movieDetailedData)
    }

    fun putSearchRequestToDB(requestText: String) {
        itemDao.putSearchHeader(
            SearchRequestData(textRequest = requestText)
        )
    }

    fun getSearchRequests(): Flow<List<String>> = itemDao.getLastSearchRequests()

    fun getFavoriteMovies(): Flow<List<MovieBriefData>> = itemDao.getFavoritesMovies()
}
