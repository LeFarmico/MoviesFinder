package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.GroupHeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.Header
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.view.MovieViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel(), MovieViewModel {

    override val concatAdapterObservable: Single<ConcatAdapter>
    override val progressBarBehaviourSubject: BehaviorSubject<Boolean>

    @Inject lateinit var interactor: Interactor

    init {
        App.appComponent.inject(this)

        progressBarBehaviourSubject = interactor.progressBarState
        concatAdapterObservable = createConcatAdapterObserver()
    }

    fun addPaginationItems(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        interactor.updateMoviesFromApi(category, page, adapter)
    }

    private fun createConcatAdapterObserver(): Single<ConcatAdapter> {
        return Single.create { subscriber ->
            val concatAdapter = ConcatAdapter()
            setAdaptersToConcat(
                concatAdapter,
                CategoryProvider.Category.POPULAR_CATEGORY,
                CategoryProvider.Category.UPCOMING_CATEGORY,
                CategoryProvider.Category.TOP_RATED_CATEGORY,
                CategoryProvider.Category.NOW_PLAYING_CATEGORY
            )
            subscriber.onSuccess(concatAdapter)
        }
    }
    private fun setAdaptersToConcat(concatAdapter: ConcatAdapter, vararg categoryTypes: CategoryProvider.Category) {
        for (i in categoryTypes.indices) {
            loadMoviesByCategory(categoryTypes[i])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { itemList ->
                    val headerAdapter = GroupHeaderAdapter().apply {
                        setHeaderTitle(categoryTypes[i].getResource())
                    }
                    val itemsAdapter = ItemsPlaceholderAdapter(this@MovieFragmentViewModel)
                    itemsAdapter.apply {
                        this.categoryType = categoryTypes[i]
                        setItems(itemList.toMutableList())
                    }
                    concatAdapter.addAdapter(headerAdapter)
                    concatAdapter.addAdapter(itemsAdapter)
                }
        }
    }

    private fun loadMoviesByCategory(categoryType: CategoryProvider.Category): Single<List<Header>> {
        interactor.putToDbMovieCategoryFromApi(categoryType, 1)
        return interactor.getCategoriesFromDB(categoryType)
    }
}
