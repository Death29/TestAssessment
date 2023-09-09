package com.example.testassessment.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testassessment.databinding.LayoutLoadMoreBinding
import com.example.testassessment.util.source.NoDataException

class AdapterLoadMoreData(private val retry:()-> Unit): LoadStateAdapter<AdapterLoadMoreData.ViewHolder>() {
    private lateinit var binding: LayoutLoadMoreBinding

    inner class ViewHolder(retry: () -> Unit): RecyclerView.ViewHolder(binding.root){
        init {
            binding.btnRetryLoad.setOnClickListener { retry() }
        }

        fun setData(state: LoadState){
            if (state is LoadState.Error){
                if (state.error is NoDataException){
                    handlingNoData()
                    Log.d("load more", "no data")
                }else {
                    handlingErrorData()
                    Log.d("load more", "error no data")
                }
            }else {
                handlingLoadingMoreData()
                Log.d("load more", "load more data")
            }
        }

        private fun handlingLoadingMoreData() {
            binding.apply {
                pbLoading.visibility = VISIBLE
                tvLoadMore.visibility = GONE
                btnRetryLoad.visibility = GONE
            }
        }

        private fun handlingErrorData() {
            binding.apply {
                pbLoading.visibility = GONE
                tvLoadMore.visibility = VISIBLE
                btnRetryLoad.visibility = VISIBLE
            }
        }

        private fun handlingNoData() {
            binding.apply {
                pbLoading.visibility = GONE
                tvLoadMore.visibility = GONE
                btnRetryLoad.visibility = GONE
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