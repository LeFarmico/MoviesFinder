package com.lefarmico.moviesfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lefarmico.moviesfinder.App
import com.lefarmico.moviesfinder.data.Interactor
import com.lefarmico.moviesfinder.data.MainRepository
import com.lefarmico.moviesfinder.data.appEntity.Category
import com.lefarmico.moviesfinder.providers.CategoryProvider
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel() {

    val popularCategoryModelData: MutableLiveData<Category>
    val upcomingCategoryModelData: MutableLiveData<Category>
    val topRatedCategoryModelData: MutableLiveData<Category>
    val nowPlayingCategoryModelData: MutableLiveData<Category>
    val showProgressBar = MutableLiveData<Boolean>()
    val categoriesMobileData: MutableList<MutableLiveData<Category>> = mutableListOf()

    @Inject lateinit var interactor: Interactor
    @Inject lateinit var repository: MainRepository

    init {
        App.appComponent.inject(this)
        loadDataToDb()
        popularCategoryModelData = interactor.getCategoriesFromDB(CategoryProvider.Category.POPULAR_CATEGORY)
        upcomingCategoryModelData = interactor.getCategoriesFromDB(CategoryProvider.Category.UPCOMING_CATEGORY)
        topRatedCategoryModelData = interactor.getCategoriesFromDB(CategoryProvider.Category.TOP_RATED_CATEGORY)
        nowPlayingCategoryModelData = interactor.getCategoriesFromDB(CategoryProvider.Category.NOW_PLAYING_CATEGORY)

        addCategoryToMobileData(
            popularCategoryModelData,
            upcomingCategoryModelData,
            topRatedCategoryModelData,
            nowPlayingCategoryModelData
        )
    }
    private fun loadDataToDb() {
        loadCategory(
            CategoryProvider.Category.POPULAR_CATEGORY,
            CategoryProvider.Category.UPCOMING_CATEGORY,
            CategoryProvider.Category.TOP_RATED_CATEGORY,
            CategoryProvider.Category.NOW_PLAYING_CATEGORY
        )
    }
    fun showProgressBar() {
        showProgressBar.postValue(true)
    }
    fun hideProgressBar() {
        showProgressBar.postValue(false)
    }

    private fun loadCategory(vararg categoryType: CategoryProvider.Category) {
        for (i in categoryType.indices) {
            interactor.getMovieCategoryFromApi(
                categoryType[i], 1,
                object : ApiCallback {
                    override fun onSuccess() {
//                        categoriesMobileData.add(interactor.getCategoriesFromDB(categoryType[i]))
                        hideProgressBar()
                    }

                    override fun onFailure() {

                    }
                }
            )
        }
    }
    private fun addCategoryToMobileData(vararg categoryMobileData: MutableLiveData<Category>) {
        for (i in categoryMobileData.indices) {
            categoriesMobileData.add(categoryMobileData[i])
        }
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}
