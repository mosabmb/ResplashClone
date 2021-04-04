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
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.databinding.FragmentPhotoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment(R.layout.fragment_photo_details) {

    private var _binding : FragmentPhotoDetailsBinding? = null
    private val binding get() = _binding!!
    val args: PhotoDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPhotoDetailsBinding.bind(view)


        binding.apply {
            Glide.with(imPhoto)
                .load(args.photo.urls.regular)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_launcher_background)
                .into(imPhoto)

            Glide.with(ivUserProfileImage)
                .load(args.photo.user.profile_image.medium)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.circleCropTransform())
                .into(ivUserProfileImage)

            tvUsername.text = args.photo.user.first_name
            tvUsername.append(" "+args.photo.user.last_name)

        }


        binding.ivUserProfileImage.setOnClickListener {
            val action = PhotoDetailsFragmentDirections.actionPhotoDetailsFragmentToUserProfileFragment(args.photo.user)
            findNavController().navigate(action)
        }


    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}