package com.vasscompany.vassuniversitybaseproject.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.databinding.ItemRecyclerviewListUserNameYearsBinding
import com.vasscompany.vassuniversitybaseproject.ui.list.adapter.UserDiffCallback
import com.vasscompany.vassuniversitybaseproject.ui.list.adapter.viewholder.UserViewHolder

class UsersAdapter : ListAdapter<UserModel, UserViewHolder>(UserDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemRecyclerviewListUserNameYearsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}