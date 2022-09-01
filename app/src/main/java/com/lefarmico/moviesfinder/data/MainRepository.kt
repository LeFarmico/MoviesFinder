package com.lefarmico.moviesfinder.data

import com.lefarmico.moviesfinder.data.dao.ItemDao
import com.lefarmico.moviesfinder.data.entity.*
import com.lefarmico.moviesfinder.providers.CategoryProvider
import io.reactivex.rxjava3.core.Single

class MainRepository(private val itemDao: ItemDao) {

    fun putItemHeadersToDb(movieBriefDataList: List<MovieBriefData>) = itemDao.insertAll(movieBriefDataList)

    fun putMovieToDb(movieData: MovieData) = itemDao.insertMovie(movieData)

    fun putMoviesByCategoryToDb(movieCategoryData: MovieCategoryData, movieBriefDataList: List<MovieBriefData>) {
        movieBriefDataList.forEach { header ->
            itemDao.insertMovieByCategory(
                MovieIdByCategoryData(
                    movieId = header.itemId, categoryType = movieCategoryData.categoryName
                )
            )
        }
    }

    fun getCategoryFromDB(categoryType: CategoryProvider.Category): Single<List<MovieBriefData>> =
        itemDao.getCategory(categoryType)

    fun updateItemHeader(movieBriefData: MovieBriefData) {
        itemDao.updateMovieDetails(movieBriefData)
    }

    fun deleteMovieFromDB(movieData: MovieData) {
        itemDao.deleteMovie(movieData)
    }

    fun putSearchRequestToDB(requestText: String) {
        itemDao.putSearchHeader(
            SearchRequestData(textRequest = requestText)
        )
    }

    fun getSearchRequests(): Single<List<String>> = itemDao.getLastSearchRequests()

    fun getFavoriteMovies(): Single<List<MovieBriefData>> = itemDao.getFavoritesMovies()
}
