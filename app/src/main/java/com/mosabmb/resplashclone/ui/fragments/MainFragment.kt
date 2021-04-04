package com.mosabmb.resplashclone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.adapters.ViewPagerAdapter
import com.mosabmb.resplashclone.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        binding.pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                }
                1 -> {
                    tab.text = "Collections"
                }
            }
        }.attach()

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    findNavController().navigate(R.id.action_mainFragment_to_searchPhotosFragment)
                    true
                }
                R.id.sort -> {
                    true
                }
                else -> false
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}