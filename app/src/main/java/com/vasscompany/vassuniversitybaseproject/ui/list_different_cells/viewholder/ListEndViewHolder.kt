package com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder

import android.view.View
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowsRecyclerviewModel
import com.vasscompany.vassuniversitybaseproject.databinding.ItemRecyclerviewListDifferentCellsEndBinding
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.adapter.ListDifferentCellsAdapter

class ListEndViewHolder(view: View, listAdapterDifferentCellsListener: ListDifferentCellsAdapter.ListAdapterDifferentCellsListener) :
    ListViewHolder(view, listAdapterDifferentCellsListener) {

    private val binding = ItemRecyclerviewListDifferentCellsEndBinding.bind(view)

    override fun onBind(listRowsRecyclerviewModel: ListRowsRecyclerviewModel) {
        view.setOnClickListener {
            listAdapterDifferentCellsListener.onListAdapterDifferentCellsListenerItemClick(
                listRowsRecyclerviewModel,
                adapterPosition
            )
        }
    }
}
