package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.adapters.ItemsPlaceholderAdapter
import com.lefarmico.moviesfinder.adapters.MoviesConcatAdapterBuilder
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.providers.CategoryProvider
import com.lefarmico.moviesfinder.utils.PaginationController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel(), PaginationController {
    var concatAdapterLiveData = MutableLiveData<ConcatAdapter>()
    val progressBarState = MutableLiveData<Boolean>()

    @Inject lateinit var interactor: Interactor

    private val categoryList = listOf(
        CategoryProvider.Category.POPULAR_CATEGORY,
        CategoryProvider.Category.UPCOMING_CATEGORY,
        CategoryProvider.Category.TOP_RATED_CATEGORY,
        CategoryProvider.Category.NOW_PLAYING_CATEGORY
    )

    init {
        App.appComponent.inject(this)
        putMoviesToDbFromAPI(categoryList)
        setConcatAdapterByCategory(categoryList)
        progressBarStateObserver()
    }

    private fun setConcatAdapterByCategory(
        categoryType: List<CategoryProvider.Category>
    ): ConcatAdapter {
        val concatBuilder = MoviesConcatAdapterBuilder(this)
        val types = categoryType.map { category ->
            interactor.getCategoriesFromDb(category) { headersList ->
                concatBuilder.addHeader(category.getResource())
                concatBuilder.addMoviesByCategory(category, headersList)
                concatAdapterLiveData.postValue(concatBuilder.build())
            }
        }
        return concatBuilder.build()
    }

    private fun putMoviesToDbFromAPI(categoryList: List<CategoryProvider.Category>) {
        categoryList.forEach {
            interactor.putToDbMovieCategoryFromApi(it, 1)
        }
    }

    override fun paginateItems(
        category: CategoryProvider.Category,
        page: Int,
        paginationReceiver: ItemsPlaceholderAdapter
    ) {
        interactor.updateMoviesFromApi(category, page) { items ->
            paginationReceiver.addItems(items)
        }
    }

    private fun progressBarStateObserver() {
        interactor.loadingState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                progressBarState.postValue(it)
            }
    }
}
