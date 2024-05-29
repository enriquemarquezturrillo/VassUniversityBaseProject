package com.vasscompany.vassuniversitybaseproject.ui.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.BaseResponse
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.data.usecases.testpokemon.GetListPokemonUseCase
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataUserSession: DataUserSession,
    encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
    private val getListPokemonUseCase: GetListPokemonUseCase,
) : BaseViewModel(savedStateHandle, dataUserSession, encryptedSharedPreferencesManager) {

    private val listPokemonNamesMutableStateFlow = MutableStateFlow<ArrayList<String>>(arrayListOf())
    val listPokemonNamesStateFlow: StateFlow<ArrayList<String>> = listPokemonNamesMutableStateFlow

    fun getListPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingMutableSharedFlow.emit(true)
            getListPokemonUseCase(30, 0).collect {
                when (it) {
                    is BaseResponse.Error -> {
                        Log.d(this@ListViewModel.TAG, "l> Error: ${it.error.message}")
                        loadingMutableSharedFlow.emit(false)
                        errorMutableSharedFlow.emit(it.error)
                    }

                    is BaseResponse.Success -> {
                        loadingMutableSharedFlow.emit(false)
                        Log.d(this@ListViewModel.TAG, "l> Success ${it.data.results.size}")
                        listPokemonNamesMutableStateFlow.value = ArrayList(it.data.results.map { model -> model.name })
                    }
                }
            }
        }
    }

    fun clickPokemonPosition(position: Int) {
        Log.d(TAG, "l> Hacemos lo que consideremos con el pokemon: ${listPokemonNamesStateFlow.value[position]}")
    }

    fun longClickPokemonLambda(): (String, Int) -> Unit =
        { name, position ->
            Log.d(
                TAG,
                "l> Nos llega al viewmodel el pokemon con el nombre: $name, de la posición: $position, ya podemos hacer lo que queramos"
            )
        }

}