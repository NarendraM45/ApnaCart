package com.apnacart.domain.usecase

import com.apnacart.domain.model.Order
import com.apnacart.domain.repository.IOrderRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class GetOrdersUseCase(private val repository: IOrderRepository) {
    operator fun invoke(): Flow<Resource<List<Order>>> {
        return repository.getOrders()
    }
}
