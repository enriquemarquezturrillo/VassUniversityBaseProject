package com.vasscompany.vassuniversitybaseproject.ui.users.add

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.data.usecases.userslist.GetUserListUseCase
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        dataUserSession: DataUserSession,
        encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
        private val getUserListUseCase: GetUserListUseCase,
    ) : BaseViewModel(savedStateHandle, dataUserSession, encryptedSharedPreferencesManager) {
        private val userAddMutableSharedFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
        val userAddSharedFlow: SharedFlow<Boolean> = userAddMutableSharedFlow

        fun addUserFlow(
            name: String,
            years: Int,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                loadingMutableSharedFlow.emit(true)
                getUserListUseCase.addUserByFlow(UserModel(name, years)).collect { isOk ->
                    loadingMutableSharedFlow.emit(false)
                    Log.d(TAG, "l> Hemos añado correctamente: $isOk")
                    userAddMutableSharedFlow.emit(isOk)
                }
            }
        }
    }
