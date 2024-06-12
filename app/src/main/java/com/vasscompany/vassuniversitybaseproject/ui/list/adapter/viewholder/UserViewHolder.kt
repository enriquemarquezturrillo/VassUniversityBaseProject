package com.vasscompany.vassuniversitybaseproject.ui.list.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.databinding.ItemRecyclerviewListUserNameYearsBinding

class UserViewHolder(val binding: ItemRecyclerviewListUserNameYearsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(userModel: UserModel) {
        binding.apply {
            tvName.text = userModel.name
            tvYears.text = userModel.years.toString()
        }
    }
}