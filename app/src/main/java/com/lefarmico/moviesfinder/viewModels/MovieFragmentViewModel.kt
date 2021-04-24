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
    val channel = Channel<Category>(Channel.BUFFERED)
    val scope = CoroutineScope(Dispatchers.IO)

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)

        isLoadingProgressBarShown = interactor.isFragmentLoadingProgressBarShown
        loadMoviesForCategory(
            CategoryProvider.Category.POPULAR_CATEGORY,
            CategoryProvider.Category.UPCOMING_CATEGORY,
            CategoryProvider.Category.TOP_RATED_CATEGORY,
            CategoryProvider.Category.NOW_PLAYING_CATEGORY
        )
    }

    private fun loadMoviesForCategory(vararg categoryType: CategoryProvider.Category) {
        for (i in categoryType.indices) {
            interactor.getMovieCategoryFromApi(categoryType[i], 1)
            val category = interactor.getCategoriesFromDB(categoryType[i])
            scope.launch {
                channel.send(category)
            }
        }
    }

    fun addPaginationItems(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        interactor.updateMoviesFromApi(category, page, adapter)
    }
}
