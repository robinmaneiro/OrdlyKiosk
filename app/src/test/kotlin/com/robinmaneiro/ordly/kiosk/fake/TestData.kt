package com.robinmaneiro.ordly.kiosk.fake

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.ordly.kiosk.auth.model.TokenPairResponse
import com.robinmaneiro.ordly.kiosk.bag.model.BagItem
import com.robinmaneiro.ordly.kiosk.bag.model.BagResponse
import com.robinmaneiro.ordly.kiosk.bag.model.ItemPrice
import com.robinmaneiro.ordly.kiosk.bag.model.PriceData
import com.robinmaneiro.ordly.kiosk.bag.model.Tax
import com.robinmaneiro.ordly.kiosk.bag.model.TaxUnit
import com.robinmaneiro.ordly.kiosk.menu.model.MenuCategories
import com.robinmaneiro.ordly.kiosk.menu.model.MenuCategory
import com.robinmaneiro.ordly.kiosk.menu.model.MenuResponse

object TestData {

    fun bagItem(
        itemId: String = "item-1",
        productId: String = "product-1",
        quantity: Int = 1,
        title: String = "Burger",
        description: String = "A tasty burger",
        unitPriceCents: Int = 999,
        totalPriceCents: Int = 999,
        currencyCode: String = "GBP",
    ): BagItem {
        val tax = Tax(TaxUnit(amount = 0, rate = 0))
        return BagItem(
            itemId = itemId,
            productId = productId,
            quantity = quantity,
            title = title,
            description = description,
            _itemPrice = ItemPrice(
                unit = PriceData(currencyCode = currencyCode, _withTax = unitPriceCents, _withoutTax = unitPriceCents, tax = tax),
                total = PriceData(currencyCode = currencyCode, _withTax = totalPriceCents, _withoutTax = totalPriceCents, tax = tax)
            )
        )
    }

    fun bagResponse(
        items: List<BagItem> = listOf(bagItem()),
        totalCostCents: Int = 999,
        currencyCode: String = "GBP",
    ): BagResponse {
        val tax = Tax(TaxUnit(amount = 0, rate = 0))
        return BagResponse(
            _totalCost = PriceData(currencyCode = currencyCode, _withTax = totalCostCents, _withoutTax = totalCostCents, tax = tax),
            _items = items
        )
    }

    fun emptyBagResponse(): BagResponse = bagResponse(items = emptyList(), totalCostCents = 0)

    fun tokenPairResponse(
        accessToken: String = "access-token",
        refreshToken: String = "refresh-token",
    ) = TokenPairResponse(accessToken = accessToken, refreshToken = refreshToken)

    fun accountDetailsResponse(
        userId: String = "user-1",
        firstName: String = "Robin",
        lastName: String = "Maneiro",
        email: String = "robin@test.com",
        phone: String = "07123456789",
        bagId: String = "auth-bag-id",
        wishlistId: String = "auth-wishlist-id",
    ) = AccountDetailsResponse(
        userId = userId,
        title = "Mr",
        firstName = firstName,
        lastName = lastName,
        emailAddress = email,
        dateOfBirth = "1990-01-01",
        phoneNumber = phone,
        bagId = bagId,
        wishlistId = wishlistId
    )

    fun menuCategories(
        categories: List<MenuCategory> = listOf(
            MenuCategory(id = "cat-1", categoryName = "Burgers", isDefault = true),
            MenuCategory(id = "cat-2", categoryName = "Drinks", isDefault = false),
        ),
    ): MenuCategories {
        return MenuCategories().apply { addAll(categories) }
    }

    fun menuResponse(
        items: List<com.robinmaneiro.ordly.kiosk.menu.model.MenuProduct> = emptyList(),
        itemCount: Int = items.size,
    ) = MenuResponse(itemCount = itemCount, items = items)
}
