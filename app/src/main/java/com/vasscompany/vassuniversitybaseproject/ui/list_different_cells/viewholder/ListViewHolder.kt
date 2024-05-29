package com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowsRecyclerviewModel
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.adapter.ListDifferentCellsAdapter

abstract class ListViewHolder(
    val view: View,
    val listAdapterDifferentCellsListener: ListDifferentCellsAdapter.ListAdapterDifferentCellsListener
) :
    RecyclerView.ViewHolder(view) {

    abstract fun onBind(listRowsRecyclerviewModel: ListRowsRecyclerviewModel)

}
