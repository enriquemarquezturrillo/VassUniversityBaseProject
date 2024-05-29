package com.vasscompany.vassuniversitybaseproject.ui.list_different_cells

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowTypeItemModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells.ListRowsRecyclerviewModel
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentListDifferentCellsBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseFragment
import com.vasscompany.vassuniversitybaseproject.ui.list_different_cells.adapter.ListDifferentCellsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListDifferentCellsFragment : BaseFragment<FragmentListDifferentCellsBinding>() {

    private val listDifferentCellsViewModel: ListDifferentCellsViewModel by viewModels()
    private lateinit var listDifferentCellsAdapter: ListDifferentCellsAdapter

    override fun inflateBinding() {
        binding = FragmentListDifferentCellsBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        configRecyclerView()
    }

    private fun configRecyclerView() {
        listDifferentCellsAdapter = ListDifferentCellsAdapter(
            requireContext(),
            arrayListOf(),
            object : ListDifferentCellsAdapter.ListAdapterDifferentCellsListener {
                override fun onListAdapterDifferentCellsListenerItemClick(
                    listRowsRecyclerviewModel: ListRowsRecyclerviewModel,
                    position: Int
                ) {
                    Log.d("TAG", "l> Clickado un elemento.")
                    listDifferentCellsViewModel.clickElementPosition(position)
                }

                override fun onListAdapterDifferentCellsListenerItemClickExpand(listRowTypeItemModel: ListRowTypeItemModel, position: Int) {
                    Log.d("TAG", "l> Clickado un elemento para expandir.")
                    listRowTypeItemModel.item.expand = !listRowTypeItemModel.item.expand
                    listDifferentCellsAdapter.notifyItemChanged(position)
                    listDifferentCellsViewModel.clickElementExpandPosition(position)
                }
            }
        )

        binding?.rvItems?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listDifferentCellsAdapter
        }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            listDifferentCellsViewModel.loadingFlow.collect {
                showLoading(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listDifferentCellsViewModel.listExampleDataStateFlow.collect { dataSet ->
                listDifferentCellsAdapter.refreshData(dataSet)
            }
        }
    }

    override fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?) {
        listDifferentCellsViewModel.getDataList()
    }

    override fun configureToolbarAndConfigScreenSections() {
        fragmentLayoutWithToolbar()
        showToolbar(title = getString(R.string.list_different_cells_title_toolbar), showBack = true)
    }

}