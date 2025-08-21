package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuProducts

interface MenuRepository {
    fun getMenuItems(): MenuProducts

    companion object {
        val instance = MenuRepositoryImpl()
    }
}