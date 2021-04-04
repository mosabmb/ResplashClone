package com.mosabmb.resplashclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mosabmb.resplashclone.databinding.PhotoLoadStateFooterBinding

class PhotoLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PhotoLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = PhotoLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return LoadStateViewHolder(binding)
    }


    inner class LoadStateViewHolder(private val binding: PhotoLoadStateFooterBinding) :
            RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }

    }


}