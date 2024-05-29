package com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder

import android.view.View
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowTypeTitleModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowsRecyclerviewModel
import com.vasscompany.vassuniversitybaseproject.databinding.ItemRecyclerviewListDifferentCellsTitleBinding
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.adapter.ListDifferentCellsAdapter

class ListTitleViewHolder(view: View, listAdapterDifferentCellsListener: ListDifferentCellsAdapter.ListAdapterDifferentCellsListener) :
    ListViewHolder(view, listAdapterDifferentCellsListener) {

    private val binding = ItemRecyclerviewListDifferentCellsTitleBinding.bind(view)

    override fun onBind(listRowsRecyclerviewModel: ListRowsRecyclerviewModel) {
        view.setOnClickListener {
            listAdapterDifferentCellsListener.onListAdapterDifferentCellsListenerItemClick(
                listRowsRecyclerviewModel,
                adapterPosition
            )
        }
        if (listRowsRecyclerviewModel is ListRowTypeTitleModel) {
            binding.tvTitle.text = listRowsRecyclerviewModel.title
        } else {
            binding.clRecyclerTitle.visibility = View.GONE
        }
    }
}
