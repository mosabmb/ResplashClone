package com.mosabmb.resplashclone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.adapters.PhotoLoadStateAdapter
import com.mosabmb.resplashclone.adapters.UnsplashCollectionAdapter
import com.mosabmb.resplashclone.databinding.FragmentCollectionsBinding
import com.mosabmb.resplashclone.utils.MarginItemDecoration
import com.mosabmb.resplashclone.viewmodels.UnsplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsFragment : Fragment(R.layout.fragment_collections) {

    private var _binding : FragmentCollectionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UnsplashViewModel>()
    lateinit var adapter : UnsplashCollectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsBinding.bind(view)

        initRecyclerView()

        viewModel.collections.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        adapter.setOnItemClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCollectionPhotosFragment(it)
            findNavController().navigate(action)
        }

        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

    }

    private fun initRecyclerView(){

        adapter = UnsplashCollectionAdapter()

        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { adapter.retry() },
                footer = PhotoLoadStateAdapter { adapter.retry() },)

        binding.recyclerView.apply {
            smoothScrollToPosition(0)
            setHasFixedSize(true)
            addItemDecoration(MarginItemDecoration(40))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}