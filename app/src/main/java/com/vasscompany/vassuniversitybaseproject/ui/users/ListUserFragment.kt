package com.vasscompany.vassuniversitybaseproject.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentUsersListBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseFragment
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import com.vasscompany.vassuniversitybaseproject.ui.extensions.toastLong
import com.vasscompany.vassuniversitybaseproject.ui.users.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListUserFragment : BaseFragment<FragmentUsersListBinding>() {

    val listUserViewModel: ListUserViewModel by viewModels()
    val usersAdapter = UsersAdapter()


    override fun inflateBinding() {
        binding = FragmentUsersListBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        configRecyclerView()
    }

    private fun configRecyclerView() {
        binding?.rvUser?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            listUserViewModel.loadingFlow.collect { loading ->
                showLoading(loading)
            }
        }
        lifecycleScope.launch {
            listUserViewModel.errorFlow.collect { errorModel ->
                requireContext().toastLong(errorModel.message)
            }
        }
        lifecycleScope.launch {
            listUserViewModel.usersListSharedFlow.collect { userList ->
                updateList(userList)
            }
        }
    }

    private fun updateList(userList: ArrayList<UserModel>) {
        Log.d(TAG, "l> userlist size: ${userList.size}")
        usersAdapter.submitList(userList)
    }

    override fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?) {
        listUserViewModel.getUsersFlow()
    }

    override fun configureToolbarAndConfigScreenSections() {
        updateShowToolbarTitle(resources.getString(R.string.list_users_title_toolbar))
    }
}