package com.lefarmico.moviesfinder.data.entity

sealed class MenuItem(val menuItemType: MenuItemType) {
    data class Movies(
        val movieCategoryData: CategoryData,
        val movieBriefDataList: List<MovieBriefData>,
    ) : MenuItem(MenuItemType.Movies)
}

sealed class MenuItemType(val typeNumber: Int) {
    object Movies : MenuItemType(1)
}
