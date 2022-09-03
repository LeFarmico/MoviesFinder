package com.lefarmico.moviesfinder.data.manager

import com.lefarmico.moviesfinder.data.entity.SearchRequestData

interface SearchDataDbRepository {

    fun insertSearchRequestToDb(searchRequestData: SearchRequestData)

    fun getSearchRequestList()

    /**
     * Get last 5 SearchRequestData objects filtered by String parameter.
     */
    fun getLastFiveRequestsByParameter(parameter: String)

    /**
     * Get last 5 elements which were successfully picked after search request.
     */
    fun getLastFiveSuccessSearchResults()
}
