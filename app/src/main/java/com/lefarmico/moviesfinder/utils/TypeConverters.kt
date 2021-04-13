package com.lefarmico.moviesfinder.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lefarmico.moviesfinder.data.appEntity.Cast
import com.lefarmico.moviesfinder.data.appEntity.Provider

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
    fun fromCastToJson(cast: List<Cast>): String =
        Gson().toJson(cast)

    @TypeConverter
    fun fromJsonToCast(json: String): List<Cast> {
        val type = object : TypeToken<List<Cast>>() {}.type
        return Gson().fromJson(json, type)
    }
}

class ProviderTypeConverter {

    @TypeConverter
    fun fromProviderToJson(providerList: List<Provider>): String =
        Gson().toJson(providerList)

    @TypeConverter
    fun fromJsonToProvider(json: String): List<Provider> {
        val type = object : TypeToken<List<Provider>>() {}.type
        return Gson().fromJson(json, type)
    }
}
