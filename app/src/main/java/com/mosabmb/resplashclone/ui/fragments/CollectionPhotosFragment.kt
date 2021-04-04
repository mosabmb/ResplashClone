package com.mosabmb.resplashclone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.adapters.PhotoLoadStateAdapter
import com.mosabmb.resplashclone.adapters.UnsplashPhotoAdapter
import com.mosabmb.resplashclone.databinding.FragmentCollectionPhotosBinding
import com.mosabmb.resplashclone.utils.MarginItemDecoration
import com.mosabmb.resplashclone.viewmodels.UnsplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CollectionPhotosFragment : Fragment(R.layout.fragment_collection_photos) {

    private var _binding : FragmentCollectionPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UnsplashViewModel>()
    lateinit var adapter: UnsplashPhotoAdapter
    private val args: CollectionPhotosFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionPhotosBinding.bind(view)

        initRecyclerView()

        initOptionsMenu()

        binding.apply {
            collectionDescription.text = args.collection.description
            totalPhotos.text = (args.collection.total_photos.toString()+" Photos")
            createdBy.text = ("Curated By "+args.collection.user.first_name+" "+args.collection.user.last_name)
        }

        viewModel.getCollectionPhotos(args.collection).observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.setOnItemClickListener {
            val action = CollectionPhotosFragmentDirections.actionCollectionPhotosFragmentToPhotoDetailsFragment(it)
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

    private fun initOptionsMenu() {
        setHasOptionsMenu(true)
        binding.toolbar.inflateMenu(R.menu.collection_photos_menu)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
        binding.toolbar.title = args.collection.title
    }

    private fun initRecyclerView() {

        adapter = UnsplashPhotoAdapter()
        adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { adapter.retry() },
                footer = PhotoLoadStateAdapter { adapter.retry() },)

        binding.recyclerView.adapter = adapter

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