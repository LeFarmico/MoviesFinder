package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.providers.CategoryProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel() {

    var isLoadingProgressBarShown: Channel<Boolean>

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)
        isLoadingProgressBarShown = interactor.isFragmentLoadingProgressBarShown
    }

    fun loadMoviesForCategory(vararg categoryType: CategoryProvider.Category): Channel<Category> {
        val categoryList = mutableListOf<Category>()
        val channel = Channel<Category>(Channel.BUFFERED)
        for (i in categoryType.indices) {
            interactor.getMovieCategoryFromApi(categoryType[i], 1)
            val category = interactor.getCategoriesFromDB(categoryType[i])
            categoryList.add(category)
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                channel.send(category)
            }
        }
        return channel
    }

    suspend fun addPaginationItems(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        interactor.updateMoviesFromApi(category, page, adapter)
    }
}
