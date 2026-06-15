package com.apnacart.di

import app.cash.sqldelight.db.SqlDriver
import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.data.remote.api.supabaseClient
import com.apnacart.data.repository.*
import com.apnacart.domain.repository.*
import com.apnacart.domain.usecase.*
import com.apnacart.presentation.screens.auth.LoginViewModel
import com.apnacart.presentation.screens.cart.CartViewModel
import com.apnacart.presentation.screens.home.HomeViewModel
import com.apnacart.presentation.screens.product_detail.ProductDetailViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

fun getSharedModule() = module {
    single { supabaseClient }
    
    // Database
    single { ApnaCartDatabase(get<SqlDriver>()) }
    
    // Repositories
    single<IProductRepository> { ProductRepositoryImpl(get()) }
    single<IAuthRepository> { AuthRepositoryImpl(get()) }
    single<ICartRepository> { CartRepositoryImpl(get(), get()) }
    single<IWishlistRepository> { WishlistRepositoryImpl(get(), get()) }
    single<IPincodeRepository> { PincodeRepositoryImpl() }
    single<IOrderRepository> { OrderRepositoryImpl() }
    
    // Use Cases
    factory { GetProductsUseCase(get()) }
    factory { GetProductDetailUseCase(get()) }
    factory { SearchProductsUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { ToggleWishlistUseCase(get()) }
    factory { CheckPincodeUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetOrdersUseCase(get()) }
    
    // ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProductDetailViewModel(get(), get(), get()) }
    viewModel { CartViewModel(get()) }
}
