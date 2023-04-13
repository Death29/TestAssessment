package com.example.testassessment.screen.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testassessment.databinding.ItemListGenreBinding
import com.example.testassessment.model.response.DataGenre

class AdapterListGenre(
    private val listenerItemGenre: (DataGenre) -> Unit
) :
    RecyclerView.Adapter<AdapterListGenre.ViewHolder>() {
    private val listGenre = mutableListOf<DataGenre>()
    class ViewHolder(val view: ItemListGenreBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemListGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AdapterListGenre.ViewHolder, position: Int) {
        holder.view.run {
            tvGenre.text = listGenre[position].name

            root.setOnClickListener {
                listenerItemGenre(listGenre[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return listGenre.size
    }

    fun setDataGenre(item: List<DataGenre>){
        this.listGenre.clear()
        this.listGenre.addAll(item)
        notifyDataSetChanged()
    }
}