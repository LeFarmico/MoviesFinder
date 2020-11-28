package com.lefarmico.moviesfinder.adapterDelegates.container


import com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem

data class ParentModel(val categoryName: String = "", var items: List<MovieItem>): Container