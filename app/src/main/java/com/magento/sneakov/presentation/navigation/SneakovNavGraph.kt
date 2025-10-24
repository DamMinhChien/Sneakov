package com.magento.sneakov.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.magento.sneakov.presentation.ui.screen.auth.AuthScreen
import com.magento.sneakov.presentation.ui.screen.home.HomeScreen
import com.magento.sneakov.presentation.ui.screen.onboarding.OnboardingScreen

@Composable
fun SneakovNavGraph(navController: NavHostController, modifier: Modifier) {

    NavHost (navController = navController, startDestination = Screen.Onboarding.route, modifier = modifier){
        composable(Screen.Onboarding.route){
            OnboardingScreen{
                navController.navigate(Screen.Auth.route){
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Auth.route){
            AuthScreen(onNavigateToHome = {
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Auth.route){ inclusive = true }
                }
            })
        }

        composable(Screen.Home.route){
            HomeScreen()
        }
    }
}