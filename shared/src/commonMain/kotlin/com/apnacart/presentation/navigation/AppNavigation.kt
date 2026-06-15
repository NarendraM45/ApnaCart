package com.apnacart.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apnacart.presentation.screens.checkout.CheckoutScreen
import com.apnacart.presentation.screens.main.MainScreen
import com.apnacart.presentation.screens.orders.OrderHistoryScreen
import com.apnacart.presentation.screens.product_detail.ProductDetailScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route  // Straight to Main, no auth gate
    ) {
        // Root shell with bottom nav
        composable(Screen.Main.route) {
            MainScreen(rootNavController = navController)
        }

        // Product Detail (launched from any tab)
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Checkout
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                onOrderPlaced = {
                    navController.navigate(Screen.OrderHistory.route) {
                        popUpTo(Screen.Checkout.route) { inclusive = true }
                    }
                }
            )
        }

        // Order History
        composable(Screen.OrderHistory.route) {
            OrderHistoryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
