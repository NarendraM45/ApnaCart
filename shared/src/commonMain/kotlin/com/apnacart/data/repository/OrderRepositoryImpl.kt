package com.apnacart.data.repository

import com.apnacart.domain.model.Order
import com.apnacart.domain.repository.IOrderRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl() : IOrderRepository {
    override fun getOrders(): Flow<Resource<List<Order>>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success(emptyList())) // Dummy implementation
    }

    override fun getOrderDetail(orderId: String): Flow<Resource<Order>> = flow {
        emit(Resource.Loading())
        emit(Resource.Error("Not implemented"))
    }

    override fun placeOrder(addressId: String): Flow<Resource<Order>> = flow {
        emit(Resource.Loading())
        emit(Resource.Error("Not implemented"))
    }
}
