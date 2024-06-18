package com.vasscompany.vassuniversitybaseproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vasscompany.vassuniversitybaseproject.BuildConfig
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentDashboardBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseFragment
import com.vasscompany.vassuniversitybaseproject.ui.extensions.toastLong
import com.vasscompany.vassuniversitybaseproject.utils.WebUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(), View.OnClickListener {

    override fun inflateBinding() {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        setupListeners()
        requireContext().toastLong(BuildConfig.TOKEN)
        binding?.tvExampleArguments?.text = getString(R.string.example_string_arguments, "Juan", 2.5)
    }

    private fun setupListeners() {
        binding?.btList?.setOnClickListener(this)
        binding?.btListDifferentCells?.setOnClickListener(this)
        binding?.btCustomTab?.setOnClickListener(this)
        binding?.btNavigation?.setOnClickListener(this)
        binding?.btUsersList?.setOnClickListener(this)
    }

    override fun observeViewModel() = Unit

    override fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?) = Unit

    override fun configureToolbarAndConfigScreenSections() {
        fragmentLayoutWithToolbar()
        showToolbar(title = getString(R.string.dashboard_title_toolbar), showBack = true)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btList -> {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToListFragment())
            }

            R.id.btListDifferentCells -> {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToListDifferentCellsFragment())
            }

            R.id.btCustomTab -> {
                WebUtils.openUrlInsideApp(requireContext(), "http://www.google.com")
            }

            R.id.btNavigation -> {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToAfragment())
            }

            R.id.btUsersList -> {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToUserslist())
            }
        }
    }

}