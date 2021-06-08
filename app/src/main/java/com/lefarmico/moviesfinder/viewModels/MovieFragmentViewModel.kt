package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.adapters.TitleGroupAdapter
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.appEntity.Header
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

    override val concatAdapterObservable: Single<ConcatAdapter>
    override val progressBarBehaviourSubject: BehaviorSubject<Boolean>

    @Inject lateinit var interactor: Interactor

    init {
        App.appComponent.inject(this)

        putMoviesToDbFromAPI(
            CategoryProvider.Category.POPULAR_CATEGORY,
            CategoryProvider.Category.UPCOMING_CATEGORY,
            CategoryProvider.Category.TOP_RATED_CATEGORY,
            CategoryProvider.Category.NOW_PLAYING_CATEGORY
        )

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
        loadCategories(categoryTypes.toList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pair ->
                val titleGroupAdapter = TitleGroupAdapter().apply {
                    setHeaderTitle(pair.first.getResource())
                }
                val itemsAdapter = ItemsPlaceholderAdapter(this@MovieFragmentViewModel)
                itemsAdapter.apply {
                    this.categoryType = pair.first
                    setItems(pair.second.toMutableList())
                }
                concatAdapter.addAdapter(titleGroupAdapter)
                concatAdapter.addAdapter(itemsAdapter)
            }
    }

    private fun loadCategories(
        categoryType: List<CategoryProvider.Category>
    ): Observable<Pair<CategoryProvider.Category, List<Header>>> {
        return Observable.create { emitter ->
            categoryType.forEach { category ->
                loadMovies(category)
                    .subscribe { headerList ->
                        emitter.onNext(Pair(category, headerList))
                    }
            }
        }
    }

    private fun putMoviesToDbFromAPI(vararg categoryType: CategoryProvider.Category) {
        categoryType.forEach {
            interactor.putToDbMovieCategoryFromApi(it, 1)
        }
    }

    private fun loadMovies(categoryType: CategoryProvider.Category): Single<List<Header>> =
        interactor.getCategoriesFromDB(categoryType)
}
