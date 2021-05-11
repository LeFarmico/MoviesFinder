package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.providers.CategoryProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel() {

    var isLoadingProgressBarShown: Channel<Boolean>
    private val channel = Channel<Category>(Channel.BUFFERED)
    private val scope = CoroutineScope(Dispatchers.IO)

    val concatAdapterLiveData = MutableLiveData<ConcatAdapter>()

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
        postConcatAdapterLiveData()
    }

    fun addPaginationItems(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        interactor.updateMoviesFromApi(category, page, adapter)
    }

    private fun postConcatAdapterLiveData() {
        val concatAdapter = ConcatAdapter()
        scope.launch {
            for (element in channel) {
                setAdapters(element, concatAdapter)
                concatAdapterLiveData.postValue(concatAdapter)
            }
        }
    }
    private fun setAdapters(category: Category, concatAdapter: ConcatAdapter) {
        scope.launch {
            val headerAdapter = HeaderAdapter().apply {
                setHeaderTitle(category.titleResource)
            }
            val itemsAdapter = ItemsPlaceholderAdapter(this@MovieFragmentViewModel)

            launch {
                category.itemsList.collect { items ->
                    withContext(Dispatchers.Main) {
                        itemsAdapter.apply {
                            setItems(items.toMutableList())
                            categoryType = category.categoryType
                        }
                    }
                }
                println("smthg")
            }
            withContext(Dispatchers.Main) {
                concatAdapter.addAdapter(headerAdapter)
                concatAdapter.addAdapter(itemsAdapter)
            }
        }
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
}
