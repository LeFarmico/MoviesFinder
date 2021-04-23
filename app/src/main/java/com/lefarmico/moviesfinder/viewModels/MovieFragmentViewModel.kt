package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.providers.CategoryProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel() {

    var isLoadingProgressBarShown: Channel<Boolean>

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)
        isLoadingProgressBarShown = interactor.isLoadingProgressBarShown
    }

    fun loadMoviesForCategory(vararg categoryType: CategoryProvider.Category): Flow<Category> {
        val categoryList = mutableListOf<Category>()

        for (i in categoryType.indices) {
            interactor.getMovieCategoryFromApi(categoryType[i], 1)
            val category = interactor.getCategoriesFromDB(categoryType[i])
            categoryList.add(category)
        }
        return categoryList.asFlow()
    }
}
