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
import com.mosabmb.resplashclone.adapters.UnsplashPhotoAdapter
import com.mosabmb.resplashclone.databinding.FragmentHomeBinding
import com.mosabmb.resplashclone.utils.MarginItemDecoration
import com.mosabmb.resplashclone.viewmodels.UnsplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UnsplashViewModel>()
    lateinit var adapter: UnsplashPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        initRecyclerView()

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.setOnItemClickListener {
            val action = MainFragmentDirections.actionMainFragmentToPhotoDetailsFragment(it)
            findNavController().navigate(action)
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

        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }

    }

    private fun initRecyclerView() {

        adapter = UnsplashPhotoAdapter()

        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { adapter.retry() },
                footer = PhotoLoadStateAdapter { adapter.retry() },
        )

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