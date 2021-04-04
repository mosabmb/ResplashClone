package com.mosabmb.resplashclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mosabmb.resplashclone.R
import com.mosabmb.resplashclone.databinding.UnsplashCollectionItemBinding
import com.mosabmb.resplashclone.models.UnsplashCollection.UnsplashCollection
import com.mosabmb.resplashclone.models.UnsplashPhoto.UnsplashPhoto


class UnsplashCollectionAdapter :
    PagingDataAdapter<UnsplashCollection, UnsplashCollectionAdapter.CollectionViewHolder>(
        COLLECTIONS_COMPARATOR
    ) {

    class CollectionViewHolder(private val binding: UnsplashCollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentCollection: UnsplashCollection) {
            binding.apply {
                Glide.with(itemView)
                    .load(currentCollection.cover_photo.urls.regular)
                        .centerCrop()
                    .error(R.drawable.ic_baseline_error_24)
                        .into(collectionCoverImage)

                Glide.with(itemView)
                    .load(currentCollection.user.profile_image.medium)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_error_24)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userProfileImage)
                collectionTitle.text = currentCollection.title
                totalsPhotos.text = currentCollection.total_photos.toString()
                totalsPhotos.append(" Photos")
                username.text = currentCollection.user.first_name
                username.append(" "+currentCollection.user.last_name)

            }
        }
    }

    companion object {
        private val COLLECTIONS_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashCollection>() {
            override fun areItemsTheSame(
                oldItem: UnsplashCollection,
                newItem: UnsplashCollection
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashCollection,
                newItem: UnsplashCollection
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClickListener: ((UnsplashCollection) -> Unit)? = null

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val currentCollection = getItem(position)

        if (currentCollection != null) {
            holder.bind(currentCollection)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                if (currentCollection != null) {
                    it(currentCollection)
                }
            }
        }

    }

    fun setOnItemClickListener(listener: (UnsplashCollection) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding =
            UnsplashCollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionViewHolder(binding)
    }

}
