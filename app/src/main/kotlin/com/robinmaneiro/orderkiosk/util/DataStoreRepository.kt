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

private val tokenKey = stringPreferencesKey("token")

interface DataStoreRepository {
    suspend fun saveEncryptedToken(token: String)
    suspend fun getEncryptedToken(): String
}

class DataStoreRepositoryImpl(
    context: Context
) : DataStoreRepository {
    private val dataStore = context.dataStore
    private suspend fun getDataStore() = dataStore.data.firstOrNull()

    //region Keys

    //endregion

    override suspend fun saveEncryptedToken(token: String) {
        val encryptedToken = EncryptionUtil.encrypt(token)
        dataStore.edit {
            it[tokenKey] = encryptedToken
        }
    }

    override suspend fun getEncryptedToken(): String {
        val encryptedToken = getDataStore()?.get(tokenKey) ?: return ""
        return EncryptionUtil.decrypt(encryptedToken)
    }

    companion object {
        const val PREFERENCES_NAME = "app_data_store"
    }
}
