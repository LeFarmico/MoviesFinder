package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.providers.CategoryProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel() {

    val categoryModelData = MutableLiveData<MutableList<Category>>()
    val showProgressBar = MutableLiveData<Boolean>()

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)
//        categoryModelData.postValue(categoryModelPool)
        loadData()
    }
    private fun loadData() {
        interactor.getMovieCategoryFromApi(
            CategoryProvider.POPULAR_CATEGORY, 1,
            object : ApiCallback {
                override fun onSuccess() {
                    hideProgressBar()
                }

                override fun onFailure() {
                    showProgressBar()
                }
            }
        )
        interactor.getMovieCategoryFromApi(
            CategoryProvider.UPCOMING_CATEGORY, 1,
            object : ApiCallback {
                override fun onSuccess() {
                    hideProgressBar()
                }

                override fun onFailure() {
                    hideProgressBar()
                }
            }
        )
        interactor.getMovieCategoryFromApi(
            CategoryProvider.TOP_RATED_CATEGORY, 1,
            object : ApiCallback {
                override fun onSuccess() {
                    hideProgressBar()
                }

                override fun onFailure() {
                    hideProgressBar()
                }
            }
        )
        interactor.getMovieCategoryFromApi(
            CategoryProvider.NOW_PLAYING_CATEGORY, 1,
            object : ApiCallback {
                override fun onSuccess() {
                    hideProgressBar()
                }

                override fun onFailure() {
                    hideProgressBar()
                }
            }
        )
        GlobalScope.launch {
            delay(1000L)
            postCategoryModel(repository.returnMovieCategories())
        }
    }
    fun postCategoryModel(categories: MutableList<Category>) {
        categoryModelData.postValue(categories)
    }
    fun postCategoryAndReset(category: Category) {
        repository.clearCategories()
        repository.putCategory(category)
    }
    fun showProgressBar() {
        repository.progressBar = true
    }
    fun hideProgressBar() {
        repository.progressBar = false
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}
