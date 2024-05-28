package com.akumakeito.vyyer.presentation.scanhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.akumakeito.vyyer.databinding.FragmentScanHistoryBinding
import com.akumakeito.vyyer.presentation.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ScanHistoryFragment : Fragment() {

    private var _binding : FragmentScanHistoryBinding? = null
    private val binding : FragmentScanHistoryBinding
        get() = _binding ?: throw IllegalStateException("Uninitialized binding")

    private val viewModel by viewModels<ScanViewModel>()

    private lateinit var adapter : IdentityScanInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanHistoryBinding.inflate(inflater, container, false)
        adapter = IdentityScanInfoAdapter(requireContext())
        val footerAdapter = adapter.withLoadStateFooter(
            footer = PagingLoadStateAdapter{adapter.retry()}
        )

        val concatAdapter = ConcatAdapter(adapter, footerAdapter)

        binding.rvScanInfo.adapter = concatAdapter
        adapter.refresh()
        binding.rvScanInfo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpaceItemDecoration(20))

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            lifecycleScope.launch {
                viewModel.data.collectLatest {
                    adapter.submitData(it)

                }
            }

            lifecycleScope.launch {
                adapter.loadStateFlow.collect {
                   swipeRefresh.isRefreshing = it.source.refresh is LoadState.Loading

                }

                swipeRefresh.setOnRefreshListener {
                    adapter.retry()
                    swipeRefresh.isRefreshing = false
                }
            }
            adapter.addLoadStateListener { loadState ->
                // Check if load state is loading or not
                val isLoading = loadState.source.refresh is LoadState.Loading
                // Update your UI based on the isLoading variable
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}