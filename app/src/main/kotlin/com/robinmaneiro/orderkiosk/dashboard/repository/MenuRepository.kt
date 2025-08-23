package com.robinmaneiro.orderkiosk.dashboard.repository

import com.robinmaneiro.orderkiosk.dashboard.model.MenuProducts

interface MenuRepository {
    fun getMenuItems(): MenuProducts

    companion object {
        val instance = MenuRepositoryImpl()
    }
}