package com.vasscompany.vassuniversitybaseproject.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentListBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseFragment
import com.vasscompany.vassuniversitybaseproject.ui.extensions.toastLong
import com.vasscompany.vassuniversitybaseproject.ui.list.adapter.ListAdapterNames
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(), ListAdapterNames.ListAdapterNamesListener {

    private val listViewModel: ListViewModel by viewModels()
    private lateinit var listAdapterNames: ListAdapterNames

    override fun inflateBinding() {
        binding = FragmentListBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        configRecyclerView()
    }

    private fun configRecyclerView() {
        listAdapterNames = ListAdapterNames(arrayListOf(), this, listViewModel.longClickPokemonLambda())


        binding?.rvItems?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapterNames
        }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.loadingFlow.collect {
                showLoading(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.listPokemonNamesStateFlow.collect { dataSet ->
                listAdapterNames.refreshData(dataSet)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.errorFlow.collect { error ->
                requireContext().toastLong(error.message)
            }
        }
    }

    override fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?) {
        listViewModel.getListPokemon()
    }

    override fun configureToolbarAndConfigScreenSections() {
        fragmentLayoutWithToolbar()
        showToolbar(title = getString(R.string.list_title_toolbar), showBack = true)
    }

    override fun onItemClick(name: String, position: Int) {
        requireContext().toastLong("Hemos pulsado al pokemon: $name, de la posición $position")
        listViewModel.clickPokemonPosition(position)
    }

}