package com.vasscompany.vassuniversitybaseproject.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.SavedStateHandle
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataUserSession: DataUserSession,
    encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager
) : BaseViewModel(savedStateHandle, dataUserSession, encryptedSharedPreferencesManager) {

    private val statusCheckVersionMutableSharedFlow =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1)
    val statusCheckVersionSharedFlow: SharedFlow<Boolean> = statusCheckVersionMutableSharedFlow

    private var waitCall = true
    private var minWaitTime = true
    private var millisMinWaitTime = 2000L
    private var needshowAlertMessage = false

    fun checkVersion() {
        waitCall = false
        minWaitTime = true

        Handler(Looper.getMainLooper()).postDelayed(Runnable { minWaitTime = false }, millisMinWaitTime)
    }

    fun needWait(): Boolean {
        return waitCall || minWaitTime
    }

    fun needShowAlertMessage(): Boolean {
        return needshowAlertMessage
    }
}