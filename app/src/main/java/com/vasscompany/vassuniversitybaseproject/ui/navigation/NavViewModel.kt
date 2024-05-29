package com.vasscompany.vassuniversitybaseproject.ui.navigation

import androidx.lifecycle.SavedStateHandle
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataUserSession: DataUserSession,
    encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
) : BaseViewModel(savedStateHandle, dataUserSession, encryptedSharedPreferencesManager)