package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.HeaderAdapter
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.view.MovieViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel(), MovieViewModel {

    override val concatAdapterData: Single<ConcatAdapter>
    override val progressBar: BehaviorSubject<Boolean>

    private var categories: Observable<Category>

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)

        categories = loadMoviesForCategory(
            CategoryProvider.Category.POPULAR_CATEGORY,
            CategoryProvider.Category.UPCOMING_CATEGORY,
            CategoryProvider.Category.TOP_RATED_CATEGORY,
            CategoryProvider.Category.NOW_PLAYING_CATEGORY
        )
        progressBar = interactor.progressBarState
        concatAdapterData = postConcatAdapterLiveData()
    }

    fun addPaginationItems(category: CategoryProvider.Category, page: Int, adapter: ItemsPlaceholderAdapter) {
        interactor.updateMoviesFromApi(category, page, adapter)
    }

    private fun postConcatAdapterLiveData(): Single<ConcatAdapter> {
        return Single.create { subscriber ->
            val concatAdapter = ConcatAdapter()
            categories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setAdapters(it, concatAdapter) }
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

    private fun loadMoviesForCategory(vararg categoryType: CategoryProvider.Category): Observable<Category> {
        return Observable.create {
            for (i in categoryType.indices) {
                interactor.getMovieCategoryFromApi(categoryType[i], 1)
                val category = interactor.getCategoriesFromDB(categoryType[i])
                it.onNext(category)
            }
            it.onComplete()
        }
    }
}
