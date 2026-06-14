package com.apnacart.domain.repository

import com.apnacart.domain.model.Order
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IOrderRepository {
    fun getOrders(): Flow<Resource<List<Order>>>
    fun getOrderDetail(orderId: String): Flow<Resource<Order>>
    fun placeOrder(addressId: String): Flow<Resource<Order>>
}
