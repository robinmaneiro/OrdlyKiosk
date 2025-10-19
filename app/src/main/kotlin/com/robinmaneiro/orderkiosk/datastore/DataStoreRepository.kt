package com.robinmaneiro.orderkiosk.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.robinmaneiro.orderkiosk.account.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepositoryImpl.Companion.PREFERENCES_NAME
import kotlinx.coroutines.flow.firstOrNull

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

//region Encrypted
private val authAccessTokenKey = stringPreferencesKey("auth_access_token")
private val authRefreshTokenKey = stringPreferencesKey("auth_refresh_token")

private val guestAccessTokenKey = stringPreferencesKey("guest_access_token")
private val guestRefreshTokenKey = stringPreferencesKey("guest_refresh_token")
//endregion

//region Plain
private val userId = stringPreferencesKey("user_id")
private val loggedInStatus = booleanPreferencesKey("logged_in_status")
private val firstName = stringPreferencesKey("first_name")
private val lastName = stringPreferencesKey("last_name")
private val emailAddress = stringPreferencesKey("email_address")
private val phoneDialingCode = stringPreferencesKey("phone_dialing_code")
private val phoneNumber = stringPreferencesKey("phone_number")
private val phoneAlpha2CountryCode = stringPreferencesKey("phone_alpha_2_country_code")
private val dateOfBirth = stringPreferencesKey("date_of_birth")
//endregion

interface DataStoreRepository {
    suspend fun saveAuthTokenPair(accessToken: String, refreshToken: String)
    suspend fun getAuthAccessToken(): String
    suspend fun getAuthRefreshToken(): String

    suspend fun saveGuestSessionPair(accessToken: String, refreshToken: String)
    suspend fun getGuestAccessToken(): String
    suspend fun getGuestRefreshToken(): String

    suspend fun saveAccountDetails(accountDetails: AccountDetailsResponse)
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
        dataStore.edit {
            it[userId] = accountDetails.userId
            it[firstName] = accountDetails.firstName
            it[lastName] = accountDetails.lastName
            it[phoneNumber] = accountDetails.phoneNumber // TODO: This will in the future include a more complex object
            it[emailAddress] = accountDetails.emailAddress
            it[dateOfBirth] = accountDetails.dateOfBirth.orEmpty()
        }
    }

    companion object {
        const val PREFERENCES_NAME = "app_data_store"
    }
}
