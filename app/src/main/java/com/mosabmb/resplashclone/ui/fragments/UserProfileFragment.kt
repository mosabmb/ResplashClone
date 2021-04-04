package com.mosabmb.resplashclone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.adapters.UserProfilePagerAdapter
import com.mosabmb.resplashclone.adapters.ViewPagerAdapter
import com.mosabmb.resplashclone.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private var _binding : FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val args: UserProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserProfileBinding.bind(view)
        initOptionsMenu()

        val user = args.user

        binding.apply {
            Glide.with(userProfileImage)
                .load(user.profile_image.medium)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.circleCropTransform())
                .into(userProfileImage)
            totalCollectionsValue.text = user.total_collections.toString()
            totalPhotosValue.text = user.total_photos.toString()
            likesValue.text = user.total_likes.toString()
            username.text = user.first_name
            username.append(" "+user.last_name)
            userBio.text = user.bio
        }

    }

    private fun initOptionsMenu() {
        setHasOptionsMenu(true)
        binding.toolbar.inflateMenu(R.menu.collection_photos_menu)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}