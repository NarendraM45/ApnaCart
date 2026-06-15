package com.apnacart.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apnacart.presentation.screens.cart.CartScreen
import com.apnacart.presentation.screens.categories.CategoriesScreen
import com.apnacart.presentation.screens.home.HomeScreen
import com.apnacart.presentation.screens.profile.ProfileScreen
import com.apnacart.presentation.screens.wishlist.WishlistScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { productId ->
                    rootNavController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(
                onProductClick = { productId ->
                    rootNavController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                onCheckoutClick = {
                    rootNavController.navigate(Screen.Checkout.route)
                }
            )
        }
        composable(Screen.Wishlist.route) {
            WishlistScreen(
                onProductClick = { productId ->
                    rootNavController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}
