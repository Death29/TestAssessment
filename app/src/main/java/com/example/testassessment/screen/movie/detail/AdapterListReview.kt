package com.example.testassessment.screen.movie.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.testassessment.R
import com.example.testassessment.databinding.ItemListReviewsBinding
import com.example.testassessment.model.response.ResultsReview
import com.example.testassessment.util.Constants
import javax.inject.Inject

class AdapterListReview @Inject constructor() :
    PagingDataAdapter<ResultsReview, AdapterListReview.ViewHolder>(differCallback) {

    private lateinit var binding: ItemListReviewsBinding
    private lateinit var context: Context

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsReview){
            binding.apply {
                val avatar = Constants.POSTER_BASE_URL+item.authorDetails.avatarPath
                ivAuthor.load(avatar){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    scale(Scale.FILL)
                }

                tvAuthor.text = item.author.toString()
                tvDate.text = item.updatedAt?.substringBefore("T")
                tvRating.text = item.authorDetails.rating.toString()
                tvComment.text = item.content.toString()
            }
        }
    }

    override fun onBindViewHolder(holder: AdapterListReview.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterListReview.ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        binding = ItemListReviewsBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    companion object{
        val differCallback = object : DiffUtil.ItemCallback<ResultsReview>(){
            override fun areItemsTheSame(oldItem: ResultsReview, newItem: ResultsReview): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: ResultsReview, newItem: ResultsReview): Boolean {
                return oldItem == newItem
            }

        }
    }
}