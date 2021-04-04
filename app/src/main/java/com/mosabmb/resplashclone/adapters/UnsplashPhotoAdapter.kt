package com.mosabmb.resplashclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.databinding.UnsplashPhotoItemBinding
import com.mosabmb.resplashclone.models.UnsplashPhoto.UnsplashPhoto


class UnsplashPhotoAdapter: PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(
        PHOTO_COMPARATOR){

    class PhotoViewHolder(private val binding: UnsplashPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: UnsplashPhoto){
            binding.apply {


                Glide.with(itemView)
                        .load(photo.urls.small)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(unsplashPhoto)

                username.text = photo.user.first_name
                username.append(" " + photo.user.last_name)

                Glide.with(itemView)
                    .load(photo.user.profile_image.medium)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_error_24)
                    .apply(RequestOptions.circleCropTransform())
                    .into(userProfileImage)


            }
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>(){
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                    oldItem: UnsplashPhoto,
                    newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClickListener: ((UnsplashPhoto) -> Unit)? = null

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentPhoto = getItem(position)
        if ( currentPhoto != null ){
            holder.bind(currentPhoto)
        }

        // setFadeAnimation(holder.itemView);

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                if (currentPhoto != null) {
                    it(currentPhoto)
                }
            }
        }

    }

    fun setOnItemClickListener(listener: (UnsplashPhoto) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = UnsplashPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

}