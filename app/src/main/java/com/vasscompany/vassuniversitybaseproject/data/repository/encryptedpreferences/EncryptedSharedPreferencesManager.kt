package com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences

import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedSharedPreferencesManager @Inject constructor(private val encryptedSharedPreferences: EncryptedSharedPreferences) {

    fun <T : Any?> set(key: String, value: T) {
        setValue(key, value)
    }

    private fun edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.encryptedSharedPreferences.edit()
        operation(editor)
        editor.apply()
    }

    private fun setValue(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value.toInt()) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value.toFloat()) }
            is Long -> edit { it.putLong(key, value.toLong()) }
            else -> {
                Log.e(TAG, "l> SharedPrefeExtensions Unsupported Type: $value")
            }
        }
    }

    fun contains(key: String): Boolean {
        return encryptedSharedPreferences.contains(key)
    }

    fun remove(key: String) {
        edit { it.remove(key) }
    }

    fun clear() {
        edit() { it.clear() }
    }

    fun saveStringEncryptedSharedPreferences(key: String, value: String) {
        val start = System.currentTimeMillis()
        set(key, value)
        Log.d(TAG, "l> time - saveStringToDataStore key: $key, ${System.currentTimeMillis() - start}ms")
    }

    fun saveBooleanEncryptedSharedPreferences(key: String, value: Boolean) {
        val start = System.currentTimeMillis()
        set(key, value)
        Log.d(TAG, "l> time - saveBooleanToDataStore key: $key, ${System.currentTimeMillis() - start}ms")
    }

    fun saveIntEncryptedSharedPreferences(key: String, value: Int) {
        val start = System.currentTimeMillis()
        set(key, value)
        Log.d(TAG, "l> time - saveIntToDataStore key: $key, ${System.currentTimeMillis() - start}ms")
    }

    fun getStringEncryptedSharedPreferences(key: String, defaultValue: String = ""): String {
        val start = System.currentTimeMillis()
        val prueba = encryptedSharedPreferences.getString(key, defaultValue) ?: ""
        Log.d(TAG, "l> time - getStringFromDataStore key: $key, ${System.currentTimeMillis() - start}ms")
        return prueba
    }

    fun getBooleanEncryptedSharedPreferences(key: String, defaultValue: Boolean = false): Boolean {
        val start = System.currentTimeMillis()
        val prueba = encryptedSharedPreferences.getBoolean(key, defaultValue)
        Log.d(TAG, "l> time - getBooleanFromDataStore key: $key, ${System.currentTimeMillis() - start}ms")
        return prueba
    }

    fun getIntEncryptedSharedPreferences(key: String, defaultValue: Int): Int {
        val start = System.currentTimeMillis()
        val prueba = encryptedSharedPreferences.getInt(key, defaultValue)
        Log.d(TAG, "l> time - getFirstIntFromDataStore key: $key, ${System.currentTimeMillis() - start}ms")
        return prueba
    }

    fun forgetFingerprintPreferences() {
        set(EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_PASSWORD, "")
        set(EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_LOGIN_ENABLED, false)
    }

    fun forgetUser() {
        forgetFingerprintPreferences()
        set(EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_NIE, "")
        set(EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_USER_NAME, "")
        set(EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_USER_ALREADY_ASKED_WANT_ACTIVATE_BIOMETRICS, false)
    }
}
