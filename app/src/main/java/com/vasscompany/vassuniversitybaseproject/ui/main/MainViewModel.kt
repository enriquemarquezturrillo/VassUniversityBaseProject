package com.vasscompany.vassuniversitybaseproject.ui.main

import androidx.lifecycle.SavedStateHandle
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesKeys
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesKeys.Companion.ENCRYPTED_SHARED_PREFERENCES_KEY_USER_EMAIL
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesKeys.Companion.ENCRYPTED_SHARED_PREFERENCES_KEY_USER_NAME
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataUserSession: DataUserSession,
    encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager
) : BaseViewModel(savedStateHandle, dataUserSession, encryptedSharedPreferencesManager) {

    fun getUserName(): String {
        return encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_KEY_USER_NAME)
    }

    fun getUserEmail(): String {
        return encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_KEY_USER_EMAIL)
    }

    private fun getNieFromDataStore(): String {
        return encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_NIE
        )
    }
}