package com.vasscompany.vassuniversitybaseproject.ui.base

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasscompany.vassuniversitybaseproject.data.domain.model.error.ErrorModel
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel.ViewModelSavedStateHandleKey.SAVED_STATE_HANDLE_KEY_TOKEN_IB
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    val savedStateHandle: SavedStateHandle? = null,
    val dataUserSession: DataUserSession,
    val encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
) : ViewModel() {

    enum class ViewModelSavedStateHandleKey(val key: String) {
        SAVED_STATE_HANDLE_KEY_TOKEN_IB("saveStateHandleKeyTokenIb"),
    }

    protected val loadingMutableSharedFlow = MutableSharedFlow<Boolean>(replay = 0)
    val loadingFlow: SharedFlow<Boolean> = loadingMutableSharedFlow

    protected val errorMutableSharedFlow = MutableSharedFlow<ErrorModel>(replay = 0)
    val errorFlow: SharedFlow<ErrorModel> = errorMutableSharedFlow

    protected val unreadNotifyState = MutableSharedFlow<Int>()
    val unreadNotify: SharedFlow<Int> = unreadNotifyState

    open fun getKeysNeedSaveStateHandler(): ArrayList<ViewModelSavedStateHandleKey> {
        return arrayListOf(
            SAVED_STATE_HANDLE_KEY_TOKEN_IB,
        )
    }

    open fun saveDataViewModelCouldBeDestroyed(keyList: ArrayList<ViewModelSavedStateHandleKey> = arrayListOf()) {
        keyList.addAll(getKeysNeedSaveStateHandler())
        if (keyList.isNotEmpty()) {
            keyList.forEach { key ->
                when (key) {
                    SAVED_STATE_HANDLE_KEY_TOKEN_IB -> {
                        savedStateHandle?.set(SAVED_STATE_HANDLE_KEY_TOKEN_IB.key, dataUserSession.tokenIb)
                    }

                    else -> Unit
                }
            }
        }
    }

    open fun restoreDataViewModelIfExists(keyList: ArrayList<ViewModelSavedStateHandleKey>) {
        if (keyList.isNotEmpty() && savedStateHandle != null) {
            keyList.forEach { key ->
                when (key) {
                    SAVED_STATE_HANDLE_KEY_TOKEN_IB -> {
                        if (savedStateHandle.contains(SAVED_STATE_HANDLE_KEY_TOKEN_IB.key)) {
                            dataUserSession.tokenIb = (savedStateHandle.get<String>(SAVED_STATE_HANDLE_KEY_TOKEN_IB.key)!!)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "l> onCleared")
    }

    fun cancelLoading() {
        viewModelScope.launch {
            loadingMutableSharedFlow.emit(false)
        }
    }
}