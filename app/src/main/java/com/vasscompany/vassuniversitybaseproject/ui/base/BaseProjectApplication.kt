package com.vasscompany.vassuniversitybaseproject.ui.base

import android.app.Application
import android.provider.Settings
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesKeys
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesKeys.Companion.ENCRYPTED_SHARED_PREFERENCES_KEY_FIREBASE_UUID
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesKeys.Companion.ENCRYPTED_SHARED_PREFERENCES_KEY_SECURE_ANDROID_ID
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class BaseProjectApplication @Inject constructor() : Application() {

    @Inject
    lateinit var encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager

    var isNetworkConnected = false

    override fun onCreate() {
        super.onCreate()
        configFirebase()
        configAndroidId()
    }

    private fun configAndroidId() {
        Log.d(TAG, "l> configAndroidId")
        CoroutineScope(Dispatchers.IO).launch {
            val secureAndroidID: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            if (encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_KEY_SECURE_ANDROID_ID)
                    .isBlank()
            ) {
                encryptedSharedPreferencesManager.saveStringEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_KEY_SECURE_ANDROID_ID,
                    secureAndroidID
                )
            }
        }
    }

    private fun configFirebase() {
        Log.d(TAG, "l> configFirebase")
        FirebaseApp.initializeApp(this.applicationContext)
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        FirebaseInstallations.getInstance().id.addOnCompleteListener { idResult ->
            CoroutineScope(Dispatchers.IO).launch {
                val uuid = if (!idResult.result.isNullOrEmpty()) {
                    idResult.result.toString()
                } else {
                    ""
                }
                encryptedSharedPreferencesManager.saveStringEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_KEY_FIREBASE_UUID, uuid)
                Log.d(this@BaseProjectApplication.TAG, "l> configFirebase uuid: $uuid")
            }
        }
    }

    fun getBearerTokenMock(): String {
        return ""
    }

    fun getBearerTokenDes(): String {
        return "Desarrollo"
    }

    fun getBearerTokenPro(): String {
        return "Produccion"
    }

    fun saveAuthToken(token: String) {
        encryptedSharedPreferencesManager.saveStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_LOGIN_AUTH, token
        )
    }

    fun getAuthToken(): String {
        return encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_LOGIN_AUTH
        )
    }
}