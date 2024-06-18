package com.vasscompany.vassuniversitybaseproject.ui.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel

class UserDiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }
}