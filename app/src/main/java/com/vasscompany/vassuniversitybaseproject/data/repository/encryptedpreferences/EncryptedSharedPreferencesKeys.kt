package com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences

class EncryptedSharedPreferencesKeys {
    companion object {
        //Config App
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_SECURE_ANDROID_ID = "encryptedSharedPreferencesKeySecureAndroidId"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_FIREBASE_UUID = "encryptedSharedPreferencesKeyFirebaseUUID"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_COUNT_ENTRIES_APP = "encryptedSharedPreferencesKeyCountEntriesApp"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_GLOBAL_POSITION_SELECT_FIAT_OR_CRYPTO =
            "encryptedSharedPreferencesKeyGlobalPositionSelectFiatOrCrypto"

        //Login
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_CAN_AUTHENTICATE_STRONG =
            "encryptedSharedPreferencesKeyBiometricCanAuthenticateStrong"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_USER_ALREADY_ASKED_WANT_ACTIVATE_BIOMETRICS =
            "dataStoreUserAlreadyAskedWantActivateBiometrics"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_NIE = "encryptedSharedPreferencesKeyBiometricNie"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_PASSWORD = "encryptedSharedPreferencesKeyBiometricPassword"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_BIOMETRIC_LOGIN_ENABLED = "encryptedSharedPreferencesKeyLoginEnabled"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_USER_NAME = "encryptedSharedPreferencesKeyUserName"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_USER_EMAIL = "encryptedSharedPreferencesKeyUserEmail"
        const val ENCRYPTED_SHARED_PREFERENCES_KEY_LOGIN_AUTH = "encryptedSharedPreferencesKeyLoginAuth"
    }
}