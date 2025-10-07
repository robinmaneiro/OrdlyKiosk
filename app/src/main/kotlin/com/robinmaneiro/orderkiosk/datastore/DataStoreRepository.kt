package com.robinmaneiro.orderkiosk.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepositoryImpl.Companion.PREFERENCES_NAME
import kotlinx.coroutines.flow.firstOrNull

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

//region Encrypted
private val accessTokenKey = stringPreferencesKey("access_token")
private val refreshTokenKey = stringPreferencesKey("refresh_token")
//endregion

//region Plain
private val loggedInStatus = booleanPreferencesKey("logged_in_status")
//endregion

interface DataStoreRepository {
    suspend fun saveAccessToken(accessToken: String)
    suspend fun getAccessToken(): String

    suspend fun saveTokenPair(accessToken: String, refreshToken: String)
    suspend fun getRefreshToken(): String
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

    companion object {
        const val PREFERENCES_NAME = "app_data_store"
    }
}
