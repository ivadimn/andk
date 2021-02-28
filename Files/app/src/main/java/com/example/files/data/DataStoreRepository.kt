package com.example.files.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.files.TestApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository {

    companion object {
        private const val DATASTORE_NAME = "DataStore"
        private val KEY = stringPreferencesKey("key")
    }

    private val context = TestApplication.context
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    fun get() : Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[KEY].orEmpty()
            }
    }

    suspend fun save(text : String) {
        context.dataStore.edit {
            it[KEY] = text
        }
    }
}

