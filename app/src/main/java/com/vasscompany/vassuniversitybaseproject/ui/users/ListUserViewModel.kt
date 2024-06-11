package com.vasscompany.vassuniversitybaseproject.ui.users

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.data.usecases.userslist.GetUserListUseCase
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val usersListMutableSharedFlow: MutableSharedFlow<ArrayList<UserModel>> = MutableStateFlow(arrayListOf())
    val usersListSharedFlow: SharedFlow<ArrayList<UserModel>> = usersListMutableSharedFlow

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserListUseCase.invoke().collect { userList ->
                usersListMutableSharedFlow.emit(userList)
            }
        }
    }
}