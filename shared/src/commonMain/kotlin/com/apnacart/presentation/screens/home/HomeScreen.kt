package com.apnacart.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apnacart.domain.model.Banner
import com.apnacart.domain.model.Category
import com.apnacart.domain.model.Product
import com.apnacart.presentation.components.ProductCard
import com.apnacart.util.UiState
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onProductClick: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // ── Top bar + search ──────────────────────────────────
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ApnaCart",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onPrimary)
                }
                
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(50.dp),
                    placeholder = { Text("Search ApnaCart.in") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth().background(androidx.compose.ui.graphics.Color(0xFF232F3E)).padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Deliver to Narendra - Delhi 1100XX", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimary)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                }
            }
        }

        // ── Quick Access Chips ────────────────────────────────
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
            ) {
                val chips = listOf("Prime", "Mobiles", "Fashion", "Deals", "MiniTV", "Electronics", "Home")
                items(chips) { chip ->
                    Text(
                        text = chip,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // ── Banner Carousel ───────────────────────────────────
        item {
            when (val s = state.banners) {
                is UiState.Success -> BannerCarousel(banners = s.data)
                is UiState.Loading -> Box(Modifier.fillMaxWidth().height(180.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                else -> {}
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // ── Categories ────────────────────────────────────────
        item {
            Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(vertical = 12.dp)) {
                Text("Shop by Category", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                when (val s = state.categories) {
                    is UiState.Success -> CategoriesRow(categories = s.data)
                    is UiState.Loading -> Box(Modifier.fillMaxWidth().height(80.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                    else -> {}
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // ── Product Sections ──────────────────────────────────
        when (val s = state.featuredProducts) {
            is UiState.Loading -> item {
                Box(Modifier.fillMaxWidth().height(220.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            }
            is UiState.Error -> item {
                Text("Error: ${s.message}", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
            }
            is UiState.Success -> {
                val allProducts = s.data
                if (allProducts.size > 20) {
                    val trending = allProducts.take(6)
                    val bestSellers = allProducts.drop(6).take(6)
                    val deals = allProducts.drop(12).take(6)
                    val keepShopping = allProducts.drop(18).take(6)
                    val browsing = allProducts.drop(24).take(6)
                    val recommended = allProducts.drop(30)

                    item { ProductSectionRow("Trending products", trending, onProductClick) }
                    item { ProductSectionRow("Best Sellers in Electronics", bestSellers, onProductClick) }
                    item { ProductSectionRow("Today's Deals", deals, onProductClick) }
                    item { ProductSectionRow("Keep shopping for", keepShopping, onProductClick) }
                    item { ProductSectionRow("Inspired by your browsing history", browsing, onProductClick) }
                    
                    item {
                        Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(top = 12.dp)) {
                            Text("Recommended for you", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 12.dp))
                        }
                    }
                    items(recommended.chunked(2)) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            row.forEach { product ->
                                ProductCard(
                                    product = product,
                                    isFavorite = false,
                                    onProductClick = onProductClick,
                                    onFavoriteClick = {},
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).height(12.dp))
                    }
                } else {
                    item { ProductSectionRow("Featured Products", allProducts, onProductClick) }
                }
            }
            else -> {}
        }
    }
}

@Composable
fun ProductSectionRow(title: String, products: List<Product>, onProductClick: (String) -> Unit) {
    if (products.isEmpty()) return
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(vertical = 12.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    isFavorite = false,
                    onProductClick = onProductClick,
                    onFavoriteClick = {}
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun BannerCarousel(banners: List<Banner>) {
    if (banners.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { banners.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000L)
            val next = (pagerState.currentPage + 1) % banners.size
            pagerState.animateScrollToPage(next)
        }
    }

    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(bottom = 8.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().height(180.dp)
        ) { page ->
            AsyncImage(
                model = banners[page].imageUrl,
                contentDescription = banners[page].title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(banners.size) { i ->
                Box(
                    modifier = Modifier.size(if (pagerState.currentPage == i) 8.dp else 6.dp).clip(CircleShape).background(if (pagerState.currentPage == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                )
                if (i < banners.size - 1) Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

@Composable
fun CategoriesRow(categories: List<Category>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = category.iconUrl,
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.background)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
