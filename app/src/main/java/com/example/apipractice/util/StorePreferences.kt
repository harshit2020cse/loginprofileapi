package com.example.apipractice.util


import android.content.Context
import android.media.session.MediaSession
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StorePreferences(context: Context) {

    private val dataStore = context.createDataStore(name = "API Practice")

    /** Preference Keys For Global Settings */
    companion object StoreConfigPreferenceKeys {

        var Token = stringPreferencesKey("LOGIN_DATA_TOKEN")

        var User = stringPreferencesKey("USER_TYPE")

    }

    suspend fun setToken(loginData: String){
        dataStore.edit {
            it[Token] = loginData
        }
    }

    suspend fun setUser(userType: String){
        dataStore.edit {
            it[User] = userType
        }
    }

    val getToken : Flow<String> = dataStore.data.map {
        it[Token] ?: ""
    }

    val getUser : Flow<String> = dataStore.data.map {
        it[User] ?: ""
    }

}

