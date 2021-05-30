package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.appEntity.ItemHeaderImpl
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.view.MovieViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel(), MovieViewModel {

    override val concatAdapterData: Single<ConcatAdapter>
    override val progressBar: BehaviorSubject<Boolean>

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)

        progressBar = interactor.progressBarState
        concatAdapterData = postConcatAdapterLiveData()
    }

    fun addPaginationItems(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        interactor.updateMoviesFromApi(category, page, adapter)
    }

    private fun postConcatAdapterLiveData(): Single<ConcatAdapter> {
        return Single.create { subscriber ->
            val concatAdapter = ConcatAdapter()
            setAdapter(
                concatAdapter,
                CategoryProvider.Category.POPULAR_CATEGORY,
                CategoryProvider.Category.UPCOMING_CATEGORY,
                CategoryProvider.Category.TOP_RATED_CATEGORY,
                CategoryProvider.Category.NOW_PLAYING_CATEGORY
            )
            subscriber.onSuccess(concatAdapter)
        }
    }
    private fun setAdapter(concatAdapter: ConcatAdapter, vararg categoryTypes: CategoryProvider.Category) {
        for (i in categoryTypes.indices) {
            loadMoviesForCategory(categoryTypes[i])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { itemList ->
                    val headerAdapter = HeaderAdapter().apply {
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

    private fun loadMoviesForCategory(categoryType: CategoryProvider.Category): Single<List<ItemHeaderImpl>> {
        interactor.putToDbMovieCategoryFromApi(categoryType, 1)
        return interactor.getCategoriesFromDB(categoryType)
    }
}
