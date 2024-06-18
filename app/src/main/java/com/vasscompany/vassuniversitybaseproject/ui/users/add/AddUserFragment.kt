package com.vasscompany.vassuniversitybaseproject.ui.users.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentUsersAddBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseFragment
import com.vasscompany.vassuniversitybaseproject.ui.extensions.toastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddUserFragment : BaseFragment<FragmentUsersAddBinding>() {
    private val addViewModel: AddViewModel by viewModels()

    override fun inflateBinding() {
        binding = FragmentUsersAddBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) {
        binding?.btAddUser?.setOnClickListener {
            if (binding?.etName?.text?.isNotBlank() == true && binding?.etYears?.text?.isNotBlank() == true) {
                addViewModel.addUserFlow(
                    binding?.etName?.text.toString(),
                    binding
                        ?.etYears
                        ?.text
                        ?.toString()
                        ?.toInt() ?: 0,
                )
            }
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            addViewModel.loadingFlow.collect { loading ->
                showLoading(loading)
            }
        }
        lifecycleScope.launch {
            addViewModel.errorFlow.collect { errorModel ->
                requireContext().toastLong(errorModel.message)
            }
        }
        lifecycleScope.launch {
            addViewModel.userAddSharedFlow.collect { isOk ->
                requireContext().toastLong("Cambio realizado correctamente: $isOk")
            }
        }
    }

    override fun viewCreatedAfterSetupObserverViewModel(
        view: View,
        savedInstanceState: Bundle?,
    ) = Unit

    override fun configureToolbarAndConfigScreenSections() {
        updateShowToolbarTitle(resources.getString(R.string.user_add_title_toolbar))
    }
}
