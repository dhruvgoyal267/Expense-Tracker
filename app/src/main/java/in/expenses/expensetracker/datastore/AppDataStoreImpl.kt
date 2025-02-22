package `in`.expenses.expensetracker.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStoreImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : AppDataStore {
    companion object {
        private const val PERMISSION_KEY = "permissions"
        private const val SMS_PROCESSING_KEY = "sms_processing_key"
        private const val STORE_NAME = "app_data_store"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)
    private val permissionsKey by lazy {
        intPreferencesKey(PERMISSION_KEY)
    }

    private val smsProcessingKey by lazy {
        booleanPreferencesKey(SMS_PROCESSING_KEY)
    }

    override suspend fun permissionAsked() {
        appContext.dataStore.edit {
            it[permissionsKey] = it[permissionsKey]?.plus(1) ?: 1
        }
    }

    override suspend fun permissionAskedCount(): Int {
        return appContext.dataStore.data.map { it[permissionsKey] }.firstOrNull() ?: 0
    }

    override suspend fun isSmsProcessingDone(): Boolean {
        return appContext.dataStore.data.map { it[smsProcessingKey] }.firstOrNull() ?: false
    }

    override suspend fun smsProcessingDone() {
        appContext.dataStore.edit {
            it[smsProcessingKey] = true
        }
    }
}