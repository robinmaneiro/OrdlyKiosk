package com.robinmaneiro.orderkiosk.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.robinmaneiro.orderkiosk.util.DataStoreRepositoryImpl.Companion.PREFERENCES_NAME
import kotlinx.coroutines.flow.firstOrNull

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

private val accessTokenKey = stringPreferencesKey("access_token")
private val refreshTokenKey = stringPreferencesKey("refresh_token")

interface DataStoreRepository {
    suspend fun saveAccessToken(accessToken: String)
    suspend fun getAccessToken(): String
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun getRefreshToken(): String
}

class DataStoreRepositoryImpl(
    context: Context
) : DataStoreRepository {
    private val dataStore = context.dataStore
    private suspend fun getDataStore() = dataStore.data.firstOrNull()

    //region Keys

    //endregion

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

    override suspend fun saveRefreshToken(refreshToken: String) {
        val encryptedToken = EncryptionUtil.encrypt(refreshToken)
        dataStore.edit {
            it[refreshTokenKey] = encryptedToken
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
