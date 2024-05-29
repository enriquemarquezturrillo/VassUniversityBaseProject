package com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.viewholder

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowTypeItemModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowsRecyclerviewModel
import com.vasscompany.vassuniversitybaseproject.databinding.ItemRecyclerviewListDifferentCellsItemBinding
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.adapter.ListDifferentCellsAdapter

class ListItemViewHolder(view: View, listAdapterDifferentCellsListener: ListDifferentCellsAdapter.ListAdapterDifferentCellsListener) :
    ListViewHolder(view, listAdapterDifferentCellsListener) {

    private val binding = ItemRecyclerviewListDifferentCellsItemBinding.bind(view)

    override fun onBind(listRowsRecyclerviewModel: ListRowsRecyclerviewModel) {
        view.setOnClickListener {
            listAdapterDifferentCellsListener.onListAdapterDifferentCellsListenerItemClick(
                listRowsRecyclerviewModel,
                adapterPosition
            )
        }

        if (listRowsRecyclerviewModel is ListRowTypeItemModel) {

            if (listRowsRecyclerviewModel.item.expand) {
                binding.ivImage.visibility = View.VISIBLE
                binding.ivStar.setImageDrawable(ResourcesCompat.getDrawable(binding.ivStar.resources, android.R.drawable.star_big_on, null))
            } else {
                binding.ivImage.visibility = View.GONE
                binding.ivStar.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        binding.ivStar.resources,
                        android.R.drawable.star_big_off,
                        null
                    )
                )
            }

            binding.ivStar.setOnClickListener {
                listAdapterDifferentCellsListener.onListAdapterDifferentCellsListenerItemClickExpand(
                    listRowsRecyclerviewModel,
                    adapterPosition
                )
            }

            binding.tvNameData.text = listRowsRecyclerviewModel.item.name

            Glide.with(binding.ivImage.context)
                .load(listRowsRecyclerviewModel.item.image)
                .transform(RoundedCorners(8))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.ivImage)

        } else {
            binding.clRecyclerItem.visibility = View.GONE
        }
    }
}
