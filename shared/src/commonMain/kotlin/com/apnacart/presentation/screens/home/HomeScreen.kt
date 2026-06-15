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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // ── Top bar + search ──────────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ApnaCart",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.weight(0f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f).height(52.dp),
                    placeholder = { Text("Search products…") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        // ── Banner Carousel ───────────────────────────────────
        item {
            when (val s = state.banners) {
                is UiState.Success -> BannerCarousel(banners = s.data)
                is UiState.Loading -> Box(
                    Modifier.fillMaxWidth().height(180.dp),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
                else -> {}
            }
        }

        // ── Categories ────────────────────────────────────────
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    "Shop by Category",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                when (val s = state.categories) {
                    is UiState.Success -> CategoriesRow(categories = s.data)
                    is UiState.Loading -> Box(
                        Modifier.fillMaxWidth().height(80.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                    else -> {}
                }
            }
        }

        // ── Featured Products ─────────────────────────────────
        item {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    "Featured Products",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                when (val s = state.featuredProducts) {
                    is UiState.Success -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(s.data) { product ->
                                ProductCard(
                                    product = product,
                                    isFavorite = false,
                                    onProductClick = onProductClick,
                                    onFavoriteClick = {}
                                )
                            }
                        }
                    }
                    is UiState.Loading -> Box(
                        Modifier.fillMaxWidth().height(220.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                    is UiState.Error -> Text(
                        text = "Error: ${s.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                    else -> {}
                }
            }
        }

        // ── All Products Grid ─────────────────────────────────
        item {
            Text(
                "All Products",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
            )
        }
        when (val s = state.featuredProducts) {
            is UiState.Success -> {
                val products = s.data
                val chunked = products.chunked(2)
                items(chunked) { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
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
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            else -> {}
        }
    }
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

    Column {
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
        // Dots indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(banners.size) { i ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == i) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == i)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline
                        )
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            AssistChip(
                onClick = {},
                label = { Text(category.name) }
            )
        }
    }
}
