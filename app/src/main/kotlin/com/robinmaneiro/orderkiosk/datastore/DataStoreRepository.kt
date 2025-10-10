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
private val accessTokenKey = stringPreferencesKey("access_token")
private val refreshTokenKey = stringPreferencesKey("refresh_token")
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
    suspend fun saveAccessToken(accessToken: String)
    suspend fun getAccessToken(): String

    suspend fun saveTokenPair(accessToken: String, refreshToken: String)
    suspend fun getRefreshToken(): String

    suspend fun saveAccountDetails(accountDetails: AccountDetailsResponse)
}

class DataStoreRepositoryImpl(
    context: Context
) : DataStoreRepository {
    private val dataStore = context.dataStore
    private suspend fun getDataStore() = dataStore.data.firstOrNull()

    override suspend fun saveAccessToken(accessToken: String) {
        val encryptedToken = EncryptionUtil.encrypt(accessToken)
        dataStore.edit {
            it[accessTokenKey] = encryptedToken
        }
    }

    override suspend fun getAccessToken(): String {
        val encryptedToken = getDataStore()?.get(accessTokenKey) ?: return ""
        return EncryptionUtil.decrypt(encryptedToken)
    }

    override suspend fun saveTokenPair(accessToken: String, refreshToken: String) {
        val encryptedAccessToken = EncryptionUtil.encrypt(accessToken)
        val encryptedRefreshToken = EncryptionUtil.encrypt(refreshToken)
        dataStore.edit {
            it[accessTokenKey] = encryptedAccessToken
            it[refreshTokenKey] = encryptedRefreshToken
        }
    }

    override suspend fun getRefreshToken(): String {
        val encryptedToken = getDataStore()?.get(refreshTokenKey) ?: return ""
        return EncryptionUtil.decrypt(encryptedToken)
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
