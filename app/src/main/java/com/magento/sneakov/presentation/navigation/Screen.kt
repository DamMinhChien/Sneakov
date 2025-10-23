package com.magento.sneakov.presentation.navigation

sealed class Screen(val route: String){
    object Onboarding: Screen("onboarding")
    object Login: Screen("login")
}
