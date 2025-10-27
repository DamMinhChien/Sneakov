package com.magento.sneakov.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.magento.sneakov.presentation.ui.screen.auth.AuthScreen
import com.magento.sneakov.presentation.ui.screen.home.HomeScreen
import com.magento.sneakov.presentation.ui.screen.onboarding.OnboardingScreen
import com.magento.sneakov.presentation.ui.screen.search.SearchResultScreen
import com.magento.sneakov.presentation.ui.screen.search.SearchScreen
import com.magento.sneakov.presentation.ui.screen.search.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SneakovNavGraph(navController: NavHostController, modifier: Modifier) {

    NavHost (navController = navController, startDestination = Screen.Home.route, modifier = modifier){
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
            HomeScreen(navigateToSearchScreen = {
                navController.navigate(Screen.Search.route)
            })
        }

//        composable(Screen.Search.route){
//            SearchScreen(onSearch = {
//                navController.navigate(Screen.SearchResult.route)
//            })
//        }
//
//        composable(Screen.SearchResult.route){
//            SearchResultScreen()
//        }
        composable(Screen.Search.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.Search.route)
            }
            val viewModel: SearchViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            SearchScreen(
                viewModel = viewModel,
                onSearch = {
                    navController.navigate(Screen.SearchResult.route)
                }
            )
        }

        composable(Screen.SearchResult.route) { backStackEntry ->
            // Lấy cùng ViewModel đã được tạo ở Screen.Search
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.Search.route)
            }
            val viewModel: SearchViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            SearchResultScreen(viewModel = viewModel, onProductClick = {})
        }



    }
}