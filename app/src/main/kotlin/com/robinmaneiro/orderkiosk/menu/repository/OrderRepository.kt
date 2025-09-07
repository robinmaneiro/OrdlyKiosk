package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuProducts

interface OrderRepository {
    fun getMenuItems(): MenuProducts

    companion object Companion {
        val instance = OrderRepositoryImpl()
    }
}
