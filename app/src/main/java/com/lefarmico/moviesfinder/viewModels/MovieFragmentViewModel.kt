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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel() {

//    var isLoadingProgressBarShown: Channel<Boolean>
    private val channel = Channel<Category>(Channel.BUFFERED)
    private val scope = CoroutineScope(Dispatchers.IO)

    val concatAdapterLiveData = MutableLiveData<ConcatAdapter>()
    val concatAdapterData: Single<ConcatAdapter>
    val progressBar: BehaviorSubject<Boolean>

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)

        progressBar = interactor.progressBarState
        loadMoviesForCategory(
            CategoryProvider.Category.POPULAR_CATEGORY,
            CategoryProvider.Category.UPCOMING_CATEGORY,
            CategoryProvider.Category.TOP_RATED_CATEGORY,
            CategoryProvider.Category.NOW_PLAYING_CATEGORY
        )
        concatAdapterData = postConcatAdapterLiveData()
    }

    fun addPaginationItems(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        interactor.updateMoviesFromApi(category, page, adapter)
    }

    private fun postConcatAdapterLiveData(): Single<ConcatAdapter> {
        return Single.create { subscriber ->
            val concatAdapter = ConcatAdapter()
            scope.launch {
                for (element in channel) {
                    withContext(Dispatchers.Main) {
                        setAdapters(element, concatAdapter)
                    }
                }
            }
            subscriber.onSuccess(concatAdapter)
        }
    }
    private fun setAdapters(category: Category, concatAdapter: ConcatAdapter) {

        val headerAdapter = HeaderAdapter().apply {
            setHeaderTitle(category.titleResource)
        }
        val itemsAdapter = ItemsPlaceholderAdapter(this@MovieFragmentViewModel)
        category.itemsList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { items ->
                itemsAdapter.apply {
                    setItems(items.toMutableList())
                    categoryType = category.categoryType
                }
            }
        concatAdapter.addAdapter(headerAdapter)
        concatAdapter.addAdapter(itemsAdapter)
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
