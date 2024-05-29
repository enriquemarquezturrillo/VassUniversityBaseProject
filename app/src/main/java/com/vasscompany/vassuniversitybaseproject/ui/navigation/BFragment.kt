package com.vasscompany.vassuniversitybaseproject.ui.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentNavigationBBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseFragment
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import com.vasscompany.vassuniversitybaseproject.ui.extensions.toastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BFragment : BaseFragment<FragmentNavigationBBinding>() {

    private val navViewModel: NavViewModel by viewModels()
    private val args: BFragmentArgs by navArgs()

    override fun inflateBinding() {
        binding = FragmentNavigationBBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        binding?.btNav?.setOnClickListener {
            findNavController().navigate(BFragmentDirections.actionBfragmentToCfragment())
        }
        binding?.tvArgument?.text = args.textByArgument
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            navViewModel.loadingFlow.collect {
                showLoading(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            navViewModel.errorFlow.collect { error ->
                requireContext().toastLong(error.message)
            }
        }
    }

    override fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "l> lista de argumentos: $args")
    }

    override fun configureToolbarAndConfigScreenSections() {
        fragmentLayoutWithToolbar()
        showToolbar(title = getString(R.string.navigation_fragment_b), showBack = true)
    }

}