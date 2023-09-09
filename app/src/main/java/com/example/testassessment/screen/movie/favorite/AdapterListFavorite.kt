package com.example.testassessment.screen.movie.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.testassessment.R
import com.example.testassessment.databinding.ItemListMovieBinding
import com.example.testassessment.model.room.DataClassFavoriteDao
import com.example.testassessment.screen.movie.detail.ActivityDetailMovies
import com.example.testassessment.util.Constants

class AdapterListFavorite(val context: Context) : RecyclerView.Adapter<AdapterListFavorite.ViewHolder>() {

    private val listFavorite = mutableListOf<DataClassFavoriteDao>()

    inner class ViewHolder(private val view: ItemListMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(data: DataClassFavoriteDao) {
            view.apply {
                tvNameMovie.text = data.nameMovie
                tvRating.text = data.rating.toString()

                val poster = Constants.POSTER_BASE_URL + data.poster
                ivPosterMovie.load(poster){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    scale(Scale.FILL)
                }
                tvRelease.text = data.releaseDate

                root.setOnClickListener {
                    context.startActivity(
                        Intent(
                        context,
                        ActivityDetailMovies::class.java
                    ).putExtra(Constants.KEY_ID_MOVIE, data.idMovie))
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterListFavorite.ViewHolder =
        ViewHolder(ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AdapterListFavorite.ViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    fun setDataFav(item: List<DataClassFavoriteDao>){
        this.listFavorite.clear()
        this.listFavorite.addAll(item)
        notifyDataSetChanged()
    }
}