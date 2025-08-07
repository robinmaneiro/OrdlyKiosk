package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuItems

interface MenuRepository {
    fun getMenuItems(): MenuItems

    companion object {
        val instance = MenuRepositoryImpl()
    }
}