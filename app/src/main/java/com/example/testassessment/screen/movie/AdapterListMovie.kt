package com.example.testassessment.screen.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.testassessment.R
import com.example.testassessment.databinding.ItemListMovieBinding
import com.example.testassessment.model.response.ResponseMovieByGenre
import com.example.testassessment.model.response.ResultsMovie
import com.example.testassessment.util.Constants
import javax.inject.Inject

class AdapterListMovie @Inject constructor() :
    PagingDataAdapter<ResultsMovie, AdapterListMovie.ViewHolder>(differCallback) {

    private lateinit var binding: ItemListMovieBinding
    private lateinit var context: Context


    inner class ViewHolder:RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsMovie){
            binding.apply {
                tvNameMovie.text = item.originalTitle.toString()
                tvRating.text = item.voteAverage.toString()

                val poster = Constants.POSTER_BASE_URL + item.posterPath
                ivPosterMovie.load(poster){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    scale(Scale.FILL)
                }
                tvRelease.text = item.releaseDate.toString()

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((ResultsMovie) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResultsMovie) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: AdapterListMovie.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListMovie.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemListMovieBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    companion object{
        val differCallback = object : DiffUtil.ItemCallback<ResultsMovie>(){
            override fun areItemsTheSame(oldItem: ResultsMovie, newItem: ResultsMovie): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: ResultsMovie, newItem: ResultsMovie): Boolean {
                return oldItem == newItem
            }

        }
    }
}