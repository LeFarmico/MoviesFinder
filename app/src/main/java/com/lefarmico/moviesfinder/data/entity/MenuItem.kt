package com.lefarmico.moviesfinder.data.entity

import androidx.paging.PagingData

sealed class MenuItem(val menuItemType: MenuItemType) {
    data class Movies(
        val movieCategoryData: CategoryData,
        val movieBriefDataList: PagingData<MovieBriefData>,
    ) : MenuItem(MenuItemType.Movies)
}

sealed class MenuItemType(val typeNumber: Int) {
    object Movies : MenuItemType(1)
}
