package com.apnacart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apnacart.domain.model.Product
import com.apnacart.util.formatAsCurrency

@Composable
fun ProductCard(
    product: Product,
    isFavorite: Boolean,
    onProductClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .clickable { onProductClick(product.id) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = product.images.firstOrNull(),
                    contentDescription = product.name,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(androidx.compose.ui.graphics.Color.White)
                        .padding(8.dp)
                )
                
                IconButton(
                    onClick = { onFavoriteClick(product.id) },
                    modifier = Modifier.align(Alignment.TopEnd).size(36.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Column(modifier = Modifier.padding(8.dp)) {
                // Title
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                
                // Ratings
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⭐ ${product.rating}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = " (${product.reviewCount})",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.height(2.dp))
                Text("1K+ bought in past month", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(4.dp))
                
                // Price Row
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "₹",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "${product.price.toInt()}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (product.originalPrice != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "M.R.P: ₹${product.originalPrice.toInt()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline,
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    }
                }
                
                // Delivery
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "FREE Delivery by ApnaCart",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
                
                // CTA
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Add to cart stub */ },
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Add to cart", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
