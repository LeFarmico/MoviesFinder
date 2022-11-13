package com.lefarmico.moviesfinder.utils.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lefarmico.moviesfinder.data.entity.MovieBriefData
import com.lefarmico.moviesfinder.data.entity.MovieCastData
import com.lefarmico.moviesfinder.data.entity.MovieCrewData
import com.lefarmico.moviesfinder.data.entity.MovieProviderData

class ListOfStringsTypeConverters {

    @TypeConverter
    fun fromListToString(list: List<String>): String =
        list.joinToString(",")

    @TypeConverter
    fun fromStringToList(string: String): List<String> =
        string.split(",").toList()
}

class CastTypeConverter {

    @TypeConverter
    fun fromCastToJson(movieCastData: List<MovieCastData>): String =
        Gson().toJson(movieCastData)

    @TypeConverter
    fun fromJsonToCast(json: String): List<MovieCastData> {
        val type = object : TypeToken<List<MovieCastData>>() {}.type
        return Gson().fromJson(json, type)
    }
}

class CrewTypeConverter {

    @TypeConverter
    fun fromCastToJson(movieCrewData: List<MovieCrewData>): String =
        Gson().toJson(movieCrewData)

    @TypeConverter
    fun fromJsonToCrew(json: String): List<MovieCrewData> {
        val type = object : TypeToken<List<MovieCrewData>>() {}.type
        return Gson().fromJson(json, type)
    }
}

class MovieBriefDataConverter {

    @TypeConverter
    fun fromMovieDataToJson(movieBriefData: List<MovieBriefData>): String =
        Gson().toJson(movieBriefData)

    @TypeConverter
    fun fromJsonToMovieData(json: String): List<MovieBriefData> {
        val type = object : TypeToken<List<MovieBriefData>>() {}.type
        return Gson().fromJson(json, type)
    }
}

class ProviderTypeConverter {

    @TypeConverter
    fun fromProviderToJson(movieProviderDataList: List<MovieProviderData>): String =
        Gson().toJson(movieProviderDataList)

    @TypeConverter
    fun fromJsonToProvider(json: String): List<MovieProviderData> {
        val type = object : TypeToken<List<MovieProviderData>>() {}.type
        return Gson().fromJson(json, type)
    }
}
