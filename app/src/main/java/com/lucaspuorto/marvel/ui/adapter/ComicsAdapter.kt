package com.lucaspuorto.marvel.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.presentation.viewdata.ComicsListViewData

class ComicsAdapter :
    RecyclerView.Adapter<ComicsAdapter.DetailsCardViewHolder>() {

    private var comicsList: List<ComicsListViewData> = listOf()

    fun addComics(comics: List<ComicsListViewData>) {
        comicsList = comics
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsCardViewHolder =
        DetailsCardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_details_card, parent, false)
        )

    override fun getItemCount(): Int = comicsList.size

    override fun onBindViewHolder(holder: DetailsCardViewHolder, position: Int) = holder.bindView(comicsList[position])

    inner class DetailsCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(comic: ComicsListViewData) {

            itemView.findViewById<TextView>(R.id.ivComicTitle).text = comic.comicTitle

            Glide.with(itemView)
                .load(comic.comicImage)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(itemView.findViewById(R.id.ivComicImage))
        }
    }
}
