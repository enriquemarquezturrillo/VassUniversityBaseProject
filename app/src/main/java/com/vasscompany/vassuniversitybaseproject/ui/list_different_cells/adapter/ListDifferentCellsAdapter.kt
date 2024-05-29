package com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowTypeItemModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowsRecyclerviewModel
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder.ListEndViewHolder
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder.ListItemViewHolder
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder.ListTitleViewHolder
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder.ListViewHolder

class ListDifferentCellsAdapter(
    context: Context,
    private val dataSet: ArrayList<ListRowsRecyclerviewModel>,
    private val listAdapterDifferentCellsListener: ListAdapterDifferentCellsListener
) :
    RecyclerView.Adapter<ListViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    interface ListAdapterDifferentCellsListener {
        fun onListAdapterDifferentCellsListenerItemClick(listRowsRecyclerviewModel: ListRowsRecyclerviewModel, position: Int)
        fun onListAdapterDifferentCellsListenerItemClickExpand(listRowTypeItemModel: ListRowTypeItemModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View
        return when (viewType) {
            ListRowsRecyclerviewModel.ListRowsTypes.TYPE_TITLE.ordinal -> {
                view = layoutInflater.inflate(R.layout.item_recyclerview_list_different_cells_title, parent, false)
                ListTitleViewHolder(view, listAdapterDifferentCellsListener)
            }

            ListRowsRecyclerviewModel.ListRowsTypes.TYPE_END.ordinal -> {
                view = layoutInflater.inflate(R.layout.item_recyclerview_list_different_cells_end, parent, false)
                ListEndViewHolder(view, listAdapterDifferentCellsListener)
            }

            else -> {
                view = layoutInflater.inflate(R.layout.item_recyclerview_list_different_cells_item, parent, false)
                ListItemViewHolder(view, listAdapterDifferentCellsListener)
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun refreshData(listRowsRecyclerviewModel: ArrayList<ListRowsRecyclerviewModel>) {
        dataSet.clear()
        dataSet.addAll(listRowsRecyclerviewModel)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].getListRowType().ordinal
    }

}
