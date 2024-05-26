package com.akumakeito.vyyer.presentation.scanhistory

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akumakeito.vyyer.databinding.StateLoadBinding


class PagingLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            StateLoadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            retry
        )
    }
}


class LoadStateViewHolder(
    private val binding: StateLoadBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        Log.d("LoadStateViewHolder", "bind: $loadState")
        binding.apply {

                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error

            btnRetry.setOnClickListener {
                retry()
            }

        }
    }
}
