package com.example.apipractice.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import com.example.apipractice.data.ProfileData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class StorePreferences constructor(context: Context) {

    val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "API Practice"
    )

    /** Store Any Any with Key */
    suspend inline fun <T : Any> storeValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    /** Store Any Object with Key */
    suspend inline fun <reified T : Any> storeValue(PreferencesPair: Pair<String, T>, value: T) {
        dataStore.edit {
            it[stringPreferencesKey(PreferencesPair.first)] = Gson().toJson(value)
        }
    }

    /** Get Stored Any with Key */
    fun <T : Any> readValue(
        key: Preferences.Key<T>
    ): Flow<T?> {
        return dataStore.getFromLocalStorageAny(key)
    }

    fun <T : Any> DataStore<Preferences>.getFromLocalStorageAny(
        PreferencesKey: Preferences.Key<T>
    ) =
        data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[PreferencesKey]
        }

    /** Get Stored Object with Key */
    inline fun <reified T> readValue(
        key: Pair<String, T>
    ): Flow<T?> {
        return dataStore.getFromLocalStorage(key)
    }

    inline fun <reified T> DataStore<Preferences>.getFromLocalStorage(
        PreferencesPair: Pair<String, T>
    ) =
        data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            val jsonString = it[stringPreferencesKey(PreferencesPair.first)]
            try {
                Gson().fromJson(jsonString, T::class.java)
            } catch (exception: Exception) {
                null
            }
        }

    /** Preference Keys For Global Settings */
    companion object StoreConfigPreferenceKeys {
        var TOKEN = stringPreferencesKey("LOGIN_DATA_TOKEN")
        var USER_TYPE = stringPreferencesKey("USER_TYPE")
        var DEMAND_PROFILE_DATA = Pair("DEMAND_PROFILE_DATA", ProfileData())
    }

}