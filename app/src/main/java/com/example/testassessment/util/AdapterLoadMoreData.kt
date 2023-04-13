package com.example.testassessment.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testassessment.databinding.LayoutLoadMoreBinding

class AdapterLoadMoreData(private val retry:()-> Unit): LoadStateAdapter<AdapterLoadMoreData.ViewHolder>() {
    private lateinit var binding: LayoutLoadMoreBinding

    inner class ViewHolder(retry: () -> Unit): RecyclerView.ViewHolder(binding.root){
        init {
            binding.btnRetryLoad.setOnClickListener { retry() }
        }

        fun setData(state: LoadState){
            binding.apply {
                pbLoading.isVisible = state is LoadState.Loading
                tvLoadMore.isVisible = state is LoadState.Error
                btnRetryLoad.isVisible = state is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: AdapterLoadMoreData.ViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): AdapterLoadMoreData.ViewHolder {
        binding = LayoutLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }
}