package com.apnacart.presentation.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        topBar = { TopAppBar(title = { Text("My Cart") }) },
        bottomBar = {
            if (cartState is UiState.Success && (cartState as UiState.Success<List<CartItem>>).data.isNotEmpty()) {
                Surface(shadowElevation = 8.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", style = MaterialTheme.typography.titleMedium)
                            Text(total.formatAsCurrency(), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onCheckoutClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Proceed to Checkout")
                        }
                    }
                }
            }
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
                        Text("Your cart is empty")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data, key = { it.productId }) { item ->
                            CartItemRow(
                                cartItem = item,
                                onIncrement = { viewModel.updateQuantity(item, item.quantity + 1) },
                                onDecrement = { if (item.quantity > 1) viewModel.updateQuantity(item, item.quantity - 1) },
                                onDelete = { viewModel.removeFromCart(item.productId) }
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
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(cartItem.productId, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                Text("Qty: ${cartItem.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrement) { Icon(Icons.Default.Remove, null) }
                Text("${cartItem.quantity}", modifier = Modifier.padding(horizontal = 4.dp))
                IconButton(onClick = onIncrement) { Icon(Icons.Default.Add, null) }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
