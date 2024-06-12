package com.vasscompany.vassuniversitybaseproject.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentUsersListBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseFragment
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import com.vasscompany.vassuniversitybaseproject.ui.extensions.toastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListUserFragment : BaseFragment<FragmentUsersListBinding>() {

    val listUserViewModel: ListUserViewModel by viewModels()


    override fun inflateBinding() {
        binding = FragmentUsersListBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = Unit

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
        //TODO kiketurry refrescar la lista con los usuario nuevos.
        Log.d(TAG, "l> userlist size: ${userList.size}")
    }

    override fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?) {
        listUserViewModel.getUsersFlow()
        listUserViewModel.getUsers()
    }

    override fun configureToolbarAndConfigScreenSections() {
        updateShowToolbarTitle(resources.getString(R.string.list_users_title_toolbar))
    }
}