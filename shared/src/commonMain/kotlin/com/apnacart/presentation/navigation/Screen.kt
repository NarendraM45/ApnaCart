package com.apnacart.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main") // Bottom nav container
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Wishlist : Screen("wishlist")
    object Profile : Screen("profile")
    
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object OrderHistory : Screen("order_history")
}
