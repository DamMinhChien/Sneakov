package com.magento.sneakov.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.magento.sneakov.presentation.ui.screen.auth.AuthScreen
import com.magento.sneakov.presentation.ui.screen.home.HomeScreen
import com.magento.sneakov.presentation.ui.screen.onboarding.OnboardingScreen
import com.magento.sneakov.presentation.ui.screen.search.SearchResultScreen
import com.magento.sneakov.presentation.ui.screen.search.SearchScreen

@Composable
fun SneakovNavGraph(navController: NavHostController, modifier: Modifier) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen {
                navController.navigate(Screen.Auth.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Auth.route) {
            AuthScreen(onNavigateToHome = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Auth.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navigateToSearchScreen = {
                    navController.navigate(Screen.Search.route)
                },
                onProductClick = { product ->
                    //navController.navigate("product/${product.id}")
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(onSearchSubmit = { keyword ->
                navController.navigate(Screen.SearchResult.createRoute(keyword))
            })
        }

        composable(
            route = Screen.SearchResult.route,
            arguments = listOf(
                navArgument("keyword") { type = NavType.StringType },
                navArgument("sortField") { type = NavType.StringType },
                navArgument("sortDirection") { type = NavType.StringType },
                navArgument("page") { type = NavType.IntType },
                navArgument("pageSize") { type = NavType.IntType })
        ) { backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword") ?: ""
            val sortField = backStackEntry.arguments?.getString("sortField") ?: "created_at"
            val sortDirection = backStackEntry.arguments?.getString("sortDirection") ?: "DESC"
            val page = backStackEntry.arguments?.getInt("page") ?: 1
            val pageSize = backStackEntry.arguments?.getInt("pageSize") ?: 20

            SearchResultScreen(
                keyword = keyword,
                sortField = sortField,
                sortDirection = sortDirection,
                page = page,
                pageSize = pageSize,
                onProductClick = { product ->
                    // TODO: Điều hướng sang màn chi tiết sản phẩm
                }
            )
        }

//        composable(Screen.Search.route) { backStackEntry ->
//            val parentEntry = remember(backStackEntry) {
//                navController.getBackStackEntry(Screen.Search.route)
//            }
//            val viewModel: SearchViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
//            SearchScreen(
//                viewModel = viewModel,
//                onSearch = {
//                    navController.navigate(Screen.SearchResult.route)
//                }
//            )
//        }
//
//        composable(Screen.SearchResult.route) { backStackEntry ->
//            // Lấy cùng ViewModel đã được tạo ở Screen.Search
//            val parentEntry = remember(backStackEntry) {
//                navController.getBackStackEntry(Screen.Search.route)
//            }
//            val viewModel: SearchViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
//            SearchResultScreen(viewModel = viewModel, onProductClick = {})
//        }


    }
}