package com.robinmaneiro.ordly.kiosk.fake

import com.robinmaneiro.ordly.kiosk.datastore.AccountDetails
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository
import com.robinmaneiro.ordly.kiosk.datastore.GuestSessionDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeDataStoreRepository : DataStoreRepository {
    private val _currentToken = MutableStateFlow("")
    override val currentToken: StateFlow<String> = _currentToken

    private var authAccessToken = ""
    private var authRefreshToken = ""
    private var guestAccessToken = ""
    private var guestRefreshToken = ""
    private var userLoggedIn = false
    private val _loggedInStatus = MutableStateFlow(false)
    private var authBagId = "auth-bag-id"
    private var authWishlistId = "auth-wishlist-id"
    private var guestBagId = "guest-bag-id"
    private var guestWishlistId = "guest-wishlist-id"

    var savedAccountDetails: AccountDetails? = null
        private set

    override suspend fun saveAuthTokenPair(accessToken: String, refreshToken: String) {
        authAccessToken = accessToken
        authRefreshToken = refreshToken
        _currentToken.value = accessToken
    }

    override suspend fun removeAuthTokenPair() {
        authAccessToken = ""
        authRefreshToken = ""
        _currentToken.value = guestAccessToken
    }

    override suspend fun getAuthAccessToken(): String = authAccessToken

    override suspend fun getAuthRefreshToken(): String = authRefreshToken

    override suspend fun saveAccountDetails(accountDetails: AccountDetails) {
        savedAccountDetails = accountDetails
        userLoggedIn = true
        _loggedInStatus.value = true
        authBagId = accountDetails.bagId
        authWishlistId = accountDetails.wishlistId
    }

    override suspend fun saveGuestSessionPair(accessToken: String, refreshToken: String) {
        guestAccessToken = accessToken
        guestRefreshToken = refreshToken
        if (authAccessToken.isEmpty()) {
            _currentToken.value = accessToken
        }
    }

    override suspend fun getGuestAccessToken(): String = guestAccessToken

    override suspend fun getGuestRefreshToken(): String = guestRefreshToken

    override suspend fun saveGuestSessionDetails(guestDetails: GuestSessionDetails) {
        guestBagId = guestDetails.guestBagId
        guestWishlistId = guestDetails.guestWishlistId
    }

    override suspend fun isUserLoggedIn(): Boolean = userLoggedIn

    override fun loggedInStatus(): Flow<Boolean> = _loggedInStatus

    override suspend fun getAuthBagId(): String = authBagId

    override suspend fun getAuthWishlistId(): String = authWishlistId

    override suspend fun getGuestBagId(): String = guestBagId

    override suspend fun getGuestWishlistId(): String = guestWishlistId

    override suspend fun clearGuestSessionData() {
        guestAccessToken = ""
        guestRefreshToken = ""
        guestBagId = ""
        guestWishlistId = ""
    }

    override suspend fun clearDataStore() {
        authAccessToken = ""
        authRefreshToken = ""
        guestAccessToken = ""
        guestRefreshToken = ""
        userLoggedIn = false
        _loggedInStatus.value = false
        savedAccountDetails = null
        _currentToken.value = ""
    }

    fun setUserLoggedIn(loggedIn: Boolean) {
        userLoggedIn = loggedIn
        _loggedInStatus.value = loggedIn
    }
}
