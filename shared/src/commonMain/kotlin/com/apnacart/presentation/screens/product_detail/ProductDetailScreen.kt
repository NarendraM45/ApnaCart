package com.apnacart.presentation.screens.product_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apnacart.presentation.components.PrimaryButton
import com.apnacart.presentation.components.TopBar
import com.apnacart.util.UiState
import com.apnacart.util.formatAsCurrency
import org.koin.compose.viewmodel.koinViewModel

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

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = "Details", onBackClick = onBackClick)
        
        when (val state = productState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val product = state.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = product.images.firstOrNull(),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = product.brand, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.price.formatAsCurrency(), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = product.description ?: "No description available", style = MaterialTheme.typography.bodyMedium)
                    
                    Spacer(modifier = Modifier.weight(1f))
                    PrimaryButton(
                        text = if (addToCartState is UiState.Loading) "Adding..." else "Add to Cart",
                        onClick = { viewModel.addToCart(product.id) },
                        enabled = addToCartState !is UiState.Loading
                    )
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
