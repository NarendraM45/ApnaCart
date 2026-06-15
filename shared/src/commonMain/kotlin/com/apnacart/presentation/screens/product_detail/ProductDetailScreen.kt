package com.apnacart.presentation.screens.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apnacart.util.UiState
import com.apnacart.util.formatAsCurrency
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = koinViewModel()
) {
    val productState by viewModel.productState.collectAsState()
    val addToCartState by viewModel.addToCartState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Share, contentDescription = "Share") }
                    IconButton(onClick = {}) { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        when (val state = productState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val product = state.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(padding)
                ) {
                    // Visit Brand
                    item {
                        Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text("Visit the ${product.brand} Store", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = product.name, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            // Ratings
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "${product.rating}", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "⭐⭐⭐⭐⭐", color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.labelMedium)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "${product.reviewCount} ratings", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    // Image Carousel
                    item {
                        Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(vertical = 16.dp)) {
                            val pagerState = rememberPagerState(pageCount = { product.images.size.coerceAtLeast(1) })
                            
                            HorizontalPager(state = pagerState) { page ->
                                AsyncImage(
                                    model = product.images.getOrNull(page),
                                    contentDescription = product.name,
                                    contentScale = ContentScale.Inside,
                                    modifier = Modifier.fillMaxWidth().height(300.dp)
                                )
                            }
                            
                            // Dots
                            if (product.images.size > 1) {
                                Row(
                                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    repeat(product.images.size) { i ->
                                        Box(
                                            modifier = Modifier.size(if (pagerState.currentPage == i) 8.dp else 6.dp).clip(CircleShape).background(if (pagerState.currentPage == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Pricing Details
                    item {
                        Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(16.dp)) {
                            Row(verticalAlignment = Alignment.Top) {
                                if (product.discountPercent > 0) {
                                    Text(
                                        text = "-${product.discountPercent}%",
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Light
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text(text = "₹", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 4.dp))
                                Text(text = "${product.price.toInt()}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Medium)
                            }
                            if (product.originalPrice != null) {
                                Text(
                                    text = "M.R.P.: ₹${product.originalPrice.toInt()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Inclusive of all taxes", style = MaterialTheme.typography.labelMedium)
                            Text("EMI starts at ₹150. No Cost EMI available", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(12.dp))
                            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha=0.3f))
                        }
                    }

                    // Delivery & Stock
                    item {
                        Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(horizontal = 16.dp, vertical = 12.dp)) {
                            Row {
                                Text("FREE delivery ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Text("Tomorrow, 10 AM - 2 PM", fontWeight = FontWeight.Bold)
                            }
                            Text("Order within 2 hrs 30 mins. Details", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.outline)
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Deliver to Narendra - Delhi 1100XX", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("In stock", color = Color(0xFF007600), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            // Quantity stub
                            OutlinedButton(onClick = {}, shape = RoundedCornerShape(8.dp)) {
                                Text("Qty: 1", color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }

                    // Actions
                    item {
                        Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(16.dp)) {
                            Button(
                                onClick = { viewModel.addToCart(product.id) },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                                enabled = addToCartState !is UiState.Loading
                            ) {
                                Text(if (addToCartState is UiState.Loading) "Adding..." else "Add to Cart", color = MaterialTheme.colorScheme.onBackground)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text("Buy Now", color = MaterialTheme.colorScheme.onBackground)
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Secure transaction", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    // Description
                    item {
                        Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(16.dp)) {
                            Text("Product details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = product.description ?: "No description available", style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {}
        }
    }
}
