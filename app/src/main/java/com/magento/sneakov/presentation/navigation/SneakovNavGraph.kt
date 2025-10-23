package com.magento.sneakov.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.magento.sneakov.presentation.ui.screen.login.LoginScreen
import com.magento.sneakov.presentation.ui.screen.onboarding.OnboardingScreen

@Composable
fun SneakovNavGraph(navController: NavHostController, modifier: Modifier) {

    NavHost (navController = navController, startDestination = Screen.Onboarding.route, modifier = modifier){
        composable(Screen.Onboarding.route){
            OnboardingScreen{
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Login.route){
            LoginScreen()
        }
    }
}