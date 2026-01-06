package com.robinmaneiro.orderkiosk.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepositoryImpl.Companion.PREFERENCES_NAME
import com.robinmaneiro.orderkiosk.util.extensions.orFalse

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

//region Encrypted
private val authAccessTokenKey = stringPreferencesKey("auth_access_token")
private val authRefreshTokenKey = stringPreferencesKey("auth_refresh_token")

private val guestAccessTokenKey = stringPreferencesKey("guest_access_token")
private val guestRefreshTokenKey = stringPreferencesKey("guest_refresh_token")
//endregion

//region Plain
private val userId = stringPreferencesKey("user_id")
private val isUserLoggedIn = booleanPreferencesKey("is_user_logged_in")
private val firstName = stringPreferencesKey("first_name")
private val lastName = stringPreferencesKey("last_name")
private val emailAddress = stringPreferencesKey("email_address")
private val phoneDialingCode = stringPreferencesKey("phone_dialing_code")
private val phoneNumber = stringPreferencesKey("phone_number")
private val phoneAlpha2CountryCode = stringPreferencesKey("phone_alpha_2_country_code")
private val dateOfBirth = stringPreferencesKey("date_of_birth")
private val authBagId = stringPreferencesKey("auth_bag_id")
private val authWishlistId = stringPreferencesKey("auth_wishlist_id")
private val guestBagId = stringPreferencesKey("guest_bag_id")
private val guestWishlistId = stringPreferencesKey("guest_wishlist_id")
//endregion

interface DataStoreRepository {
    suspend fun saveAuthTokenPair(accessToken: String, refreshToken: String)
    suspend fun removeAuthTokenPair()
    suspend fun getAuthAccessToken(): String
    suspend fun getAuthRefreshToken(): String
    suspend fun saveAccountDetails(accountDetails: AccountDetailsResponse)

    suspend fun saveGuestSessionPair(accessToken: String, refreshToken: String)
    suspend fun getGuestAccessToken(): String
    suspend fun getGuestRefreshToken(): String
    suspend fun saveGuestSessionDetails(guestDetails: GuestSessionDetailsResponse)

    suspend fun isUserLoggedIn(): Boolean
    fun loggedInStatus(): Flow<Boolean>

    suspend fun getAuthBagId(): String
    suspend fun getAuthWishlistId(): String

    suspend fun getGuestBagId(): String
    suspend fun getGuestWishlistId(): String

    suspend fun clearGuestSessionData()
    suspend fun clearDataStore()
}

class DataStoreRepositoryImpl(
    context: Context
) : DataStoreRepository {
    private val dataStore = context.dataStore
    private suspend fun getDataStore() = dataStore.data.firstOrNull()

    override suspend fun getAuthAccessToken(): String {
        val encryptedToken = getDataStore()?.get(authAccessTokenKey) ?: return ""
        return EncryptionUtil.decrypt(encryptedToken)
    }

    override suspend fun saveAuthTokenPair(accessToken: String, refreshToken: String) {
        val encryptedAccessToken = EncryptionUtil.encrypt(accessToken)
        val encryptedRefreshToken = EncryptionUtil.encrypt(refreshToken)
        dataStore.edit {
            it[authAccessTokenKey] = encryptedAccessToken
            it[authRefreshTokenKey] = encryptedRefreshToken
        }
    }

    override suspend fun removeAuthTokenPair() {
        dataStore.edit { preferences ->
            preferences.remove(authAccessTokenKey)
            preferences.remove(authRefreshTokenKey)
        }
    }

    override suspend fun getAuthRefreshToken(): String {
        val encryptedToken = getDataStore()?.get(authRefreshTokenKey) ?: return ""
        return EncryptionUtil.decrypt(encryptedToken)
    }

    override suspend fun saveGuestSessionPair(accessToken: String, refreshToken: String) {
        val encryptedAccessToken = EncryptionUtil.encrypt(accessToken)
        val encryptedRefreshToken = EncryptionUtil.encrypt(refreshToken)
        dataStore.edit {
            it[guestAccessTokenKey] = encryptedAccessToken
            it[guestRefreshTokenKey] = encryptedRefreshToken
        }
    }

    override suspend fun getGuestAccessToken(): String {
        val encryptedAccessToken = getDataStore()?.get(guestAccessTokenKey) ?: return ""
        return EncryptionUtil.decrypt(encryptedAccessToken)
    }

    override suspend fun getGuestRefreshToken(): String {
        val encryptedRefreshToken = getDataStore()?.get(guestRefreshTokenKey) ?: return ""
        return EncryptionUtil.decrypt(encryptedRefreshToken)
    }

    override suspend fun saveAccountDetails(accountDetails: AccountDetailsResponse) {
        dataStore.edit { preferences ->
            preferences[userId] = accountDetails.userId
            preferences[firstName] = accountDetails.firstName
            preferences[lastName] = accountDetails.lastName
            preferences[phoneNumber] = accountDetails.phoneNumber // TODO: This will in the future include a more complex object
            preferences[emailAddress] = accountDetails.emailAddress
            preferences[dateOfBirth] = accountDetails.dateOfBirth.orEmpty()
            preferences[authBagId] = accountDetails.bagId
            preferences[authWishlistId] = accountDetails.wishlistId

            preferences[isUserLoggedIn] = true
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return getDataStore()?.get(isUserLoggedIn).orFalse()
    }

    override fun loggedInStatus(): Flow<Boolean> {
        return dataStore.data.map { it[isUserLoggedIn].orFalse() }
    }

    override suspend fun saveGuestSessionDetails(guestDetails: GuestSessionDetailsResponse) {
        dataStore.edit { preferences ->
            preferences[guestBagId] = guestDetails.guestBagId
            preferences[guestWishlistId] = guestDetails.guestWishlistId
        }
    }

    override suspend fun getAuthBagId(): String {
        return getDataStore()?.get(authBagId).orEmpty()
    }

    override suspend fun getAuthWishlistId(): String {
        return getDataStore()?.get(authWishlistId).orEmpty()
    }

    override suspend fun getGuestBagId(): String {
        return getDataStore()?.get(guestBagId).orEmpty()
    }

    override suspend fun getGuestWishlistId(): String {
        return getDataStore()?.get(guestWishlistId).orEmpty()
    }

    override suspend fun clearGuestSessionData() {
        dataStore.edit { preferences ->
            preferences.remove(guestAccessTokenKey)
            preferences.remove(guestRefreshTokenKey)
            preferences.remove(guestBagId)
            preferences.remove(guestWishlistId)
        }
    }

    override suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        const val PREFERENCES_NAME = "app_data_store"
    }
}
