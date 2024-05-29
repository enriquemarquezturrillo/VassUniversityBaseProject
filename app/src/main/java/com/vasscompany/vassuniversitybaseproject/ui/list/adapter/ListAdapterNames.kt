package com.vasscompany.vassuniversitybaseproject.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vasscompany.vassuniversitybaseproject.databinding.ItemRecyclerviewListNameBinding
import com.vasscompany.vassuniversitybaseproject.ui.list.adapter.ListAdapterNames.NameViewHolder

class ListAdapterNames(
    private val dataSet: ArrayList<String>, val listAdapterNamesListener: ListAdapterNamesListener,
    private val onLongClick: (name: String, position: Int) -> Unit
) :
    RecyclerView.Adapter<NameViewHolder>() {

    interface ListAdapterNamesListener {
        fun onItemClick(name: String, position: Int)
    }

    inner class NameViewHolder(private val binding: ItemRecyclerviewListNameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, position: Int) {
            binding.tvName.text = name
            binding.tvName.setOnClickListener {
                listAdapterNamesListener.onItemClick(name, position)
            }

            binding.tvName.setOnLongClickListener {
                onLongClick(binding.tvName.text.toString(), position)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerviewListNameBinding.inflate(layoutInflater, parent, false)
        return NameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    fun refreshData(names: ArrayList<String>) {
        dataSet.clear()
        dataSet.addAll(names)
        notifyDataSetChanged()
    }
}