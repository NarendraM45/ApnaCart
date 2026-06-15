package com.apnacart.presentation.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apnacart.domain.model.CartItem
import com.apnacart.util.UiState
import com.apnacart.util.formatAsCurrency
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onCheckoutClick: () -> Unit,
    viewModel: CartViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) { viewModel.loadCart() }
    val cartState by viewModel.cartState.collectAsState()
    val total by viewModel.totalPrice.collectAsState()

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("Cart", fontWeight = FontWeight.Normal) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) 
        }
    ) { padding ->
        when (val state = cartState) {
            is UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            is UiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            }
            is UiState.Success -> {
                if (state.data.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Your ApnaCart Cart is empty", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Shop today's deals", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(padding).background(MaterialTheme.colorScheme.background),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        item {
                            Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(16.dp)) {
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text("Subtotal ", style = MaterialTheme.typography.titleMedium)
                                    Text(total.formatAsCurrency(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("EMI Available", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = onCheckoutClick,
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                                ) {
                                    Text("Proceed to Buy (${state.data.sumOf { it.quantity }} items)", color = MaterialTheme.colorScheme.onBackground)
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        items(state.data, key = { it.id }) { item ->
                            CartItemRow(
                                cartItem = item,
                                onIncrement = { viewModel.updateQuantity(item, item.quantity + 1) },
                                onDecrement = { if (item.quantity > 1) viewModel.updateQuantity(item, item.quantity - 1) },
                                onDelete = { viewModel.removeFromCart(item.id) }
                            )
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onDelete: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = cartItem.product.images.firstOrNull(),
                contentDescription = cartItem.product.name,
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(100.dp).background(Color.White).padding(8.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    cartItem.product.name, 
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(cartItem.product.price.formatAsCurrency(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("In stock", color = Color(0xFF007600), style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text("Eligible for FREE Shipping", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Quantity Control
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)).padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                IconButton(onClick = onDecrement, modifier = Modifier.size(32.dp)) { Text("-", style = MaterialTheme.typography.titleMedium) }
                Text("${cartItem.quantity}", modifier = Modifier.padding(horizontal = 12.dp), style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = onIncrement, modifier = Modifier.size(32.dp)) { Text("+", style = MaterialTheme.typography.titleMedium) }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            TextButton(onClick = onDelete) {
                Text("Delete", color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = {}) {
                Text("Save for later", color = MaterialTheme.colorScheme.onSurface)
            }
        }
        Divider(modifier = Modifier.padding(top = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha=0.2f))
    }
}
