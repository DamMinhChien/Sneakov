package com.magento.sneakov.presentation.navigation

sealed class Screen(val route: String){
    object Onboarding: Screen("onboarding")
    object Auth: Screen("auth")
    object Home: Screen("home")
    object Search: Screen("search")
    object SearchResult: Screen("searchResult")
}
