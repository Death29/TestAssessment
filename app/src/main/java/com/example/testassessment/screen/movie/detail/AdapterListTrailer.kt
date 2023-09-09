package com.example.testassessment.screen.movie.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testassessment.databinding.ItemTrailerBinding
import com.example.testassessment.model.response.DataGenre
import com.example.testassessment.model.response.ResultsItemTrailer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback

class AdapterListTrailer() : RecyclerView.Adapter<AdapterListTrailer.ViewHolder>() {

    private val listTrailer = mutableListOf<ResultsItemTrailer>()

    inner class ViewHolder(private val item: ItemTrailerBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: ResultsItemTrailer) {
            item.apply {
                mediaPlayerVideo.apply {
                    getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(data.key, 0f)
                        }

                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AdapterListTrailer.ViewHolder, position: Int) {
        holder.bind(listTrailer[position])
    }

    override fun getItemCount(): Int {
        return listTrailer.size
    }

    fun setDataTrailer(item: List<ResultsItemTrailer>){
        this.listTrailer.clear()
        this.listTrailer.addAll(item)
        notifyDataSetChanged()
    }
}