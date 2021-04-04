package com.mosabmb.resplashclone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mosabmb.resplashclone.ui.fragments.CollectionsFragment
import com.mosabmb.resplashclone.ui.fragments.HomeFragment


class UserProfilePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->  HomeFragment()
            1 ->  CollectionsFragment()
            2 ->  CollectionsFragment()
            else -> HomeFragment()
        }
    }


}