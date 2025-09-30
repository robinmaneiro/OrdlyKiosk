package com.robinmaneiro.orderkiosk.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.robinmaneiro.orderkiosk.util.DataStoreRepositoryImpl.Companion.PREFERENCES_NAME

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

interface DataStoreRepository {
    suspend fun saveToken()
}

class DataStoreRepositoryImpl(
    context: Context
) : DataStoreRepository {
    private val dataStore = context.dataStore

    override suspend fun saveToken() {

    }

    companion object {
        const val PREFERENCES_NAME = "app_data_store"
    }
}