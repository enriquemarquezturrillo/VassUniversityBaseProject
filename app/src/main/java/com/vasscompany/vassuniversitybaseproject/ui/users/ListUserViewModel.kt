package com.vasscompany.vassuniversitybaseproject.ui.users

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
class ListUserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataUserSession: DataUserSession,
    encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
    private val getUserListUseCase: GetUserListUseCase
) : BaseViewModel(savedStateHandle, dataUserSession, encryptedSharedPreferencesManager) {

    private val usersListMutableSharedFlow: MutableSharedFlow<ArrayList<UserModel>> = MutableSharedFlow()
    val usersListSharedFlow: SharedFlow<ArrayList<UserModel>> = usersListMutableSharedFlow

    fun getUsersFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            loadingMutableSharedFlow.emit(true)
            getUserListUseCase.getUsersByFlow().collect { users ->
                usersListMutableSharedFlow.emit(users)
                loadingMutableSharedFlow.emit(false)
                Log.d(TAG, "l> Recibo userList con flow ${users.size} elementos ${System.currentTimeMillis() - time}ms")
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            loadingMutableSharedFlow.emit(true)
            val users = getUserListUseCase.getUsers()
            usersListMutableSharedFlow.emit(users)
            loadingMutableSharedFlow.emit(false)
            Log.d(TAG, "l> Recibo userList con ${users.size} elementos ${System.currentTimeMillis() - time}ms")
        }
    }
}