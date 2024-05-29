package com.vasscompany.vassuniversitybaseproject.ui.list_different_cells

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ItemModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowTypeEndModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowTypeItemModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowTypeTitleModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowsRecyclerviewModel
import com.vasscompany.vassuniversitybaseproject.data.repository.encryptedpreferences.EncryptedSharedPreferencesManager
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseViewModel
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ListDifferentCellsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataUserSession: DataUserSession,
    encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
) : BaseViewModel(savedStateHandle, dataUserSession, encryptedSharedPreferencesManager) {

    private var exampleList: ArrayList<ListRowsRecyclerviewModel> = arrayListOf()
    private val listExampleDataMutableStateFlow = MutableStateFlow<ArrayList<ListRowsRecyclerviewModel>>(arrayListOf())
    val listExampleDataStateFlow: StateFlow<ArrayList<ListRowsRecyclerviewModel>> = listExampleDataMutableStateFlow

    fun getDataList() {
        exampleList.clear()
        exampleList.add(ListRowTypeTitleModel("Vamos a tope!"))
        exampleList.add(ListRowTypeItemModel(ItemModel("Hola", 20, "https://picsum.photos/300", false)))
        exampleList.add(ListRowTypeItemModel(ItemModel("Bienvenido", 20, "https://picsum.photos/300", false)))
        exampleList.add(ListRowTypeItemModel(ItemModel("Saludos", 20, "https://picsum.photos/300", false)))
        exampleList.add(ListRowTypeItemModel(ItemModel("Que pasa Máquinas", 20, "https://picsum.photos/300", false)))
        exampleList.add(ListRowTypeItemModel(ItemModel("Vamos ahí", 20, "https://picsum.photos/300", false)))
        exampleList.add(ListRowTypeEndModel())
        listExampleDataMutableStateFlow.value = exampleList
    }

    fun clickElementPosition(position: Int) {
        Log.d(TAG, "l> Hemos pulsado el elemento: ${exampleList[position]}")
    }

    fun clickElementExpandPosition(position: Int) {
        Log.d(TAG, "l> Hemos pulsado para expandir/contraer el elemento: ${exampleList[position]}")
    }
}