package com.mosabmb.resplashclone.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.adapters.PhotoLoadStateAdapter
import com.mosabmb.resplashclone.adapters.UnsplashCollectionAdapter
import com.mosabmb.resplashclone.adapters.UnsplashPhotoAdapter
import com.mosabmb.resplashclone.databinding.FragmentSearchPhotosBinding
import com.mosabmb.resplashclone.utils.MarginItemDecoration
import com.mosabmb.resplashclone.viewmodels.UnsplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPhotosFragment : Fragment(R.layout.fragment_search_photos) {

    private var _binding : FragmentSearchPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UnsplashViewModel>()
    lateinit var adapter: UnsplashPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchPhotosBinding.bind(view)

        initToolbar()

        initRecyclerView()

        viewModel.searchPhotos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.search -> {
                    val searchItem = it
                    val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
                    searchView.setOnQueryTextListener( object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if ( query != null ) {
                                binding.recyclerView.scrollToPosition(0)
                                viewModel.searchPhotos(query)
                                searchView.clearFocus()
                            }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return true
                        }
                    })

                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

        adapter.setOnItemClickListener {
            val action = SearchPhotosFragmentDirections.actionSearchPhotosFragmentToPhotoDetailsFragment(it)
            findNavController().navigate(action)
        }

    }


    private fun initToolbar() {
        setHasOptionsMenu(true)
        binding.toolbar.inflateMenu(R.menu.search_fragment_menu)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
        binding.toolbar.title = null
    }

    private fun initRecyclerView(){

        adapter = UnsplashPhotoAdapter()

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