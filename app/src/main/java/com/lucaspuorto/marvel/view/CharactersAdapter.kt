package com.lucaspuorto.marvel.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.databinding.ItemCharcterBinding
import com.lucaspuorto.marvel.db.model.CharacterModel

class CharactersAdapter(
    private val characterClick: (CharacterModel) -> Unit,
    private val favoriteClick: (CharacterModel, Int) -> Unit,
) : ListAdapter<CharacterModel, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder.from(parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), position, characterClick, favoriteClick)
    }

    class CharacterViewHolder(private val binding: ItemCharcterBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharcterBinding.inflate(layoutInflater, parent, false)
                return CharacterViewHolder(binding)
            }
        }

        fun bind(character: CharacterModel, position: Int, characterClick: (CharacterModel) -> Unit, favoriteClick: (CharacterModel, Int) -> Unit) {
            binding.apply {
                tvCharacter.text = character.name
                Glide.with(binding.root.context)
                    .load(character.thumbnail)
                    .placeholder(R.drawable.ic_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.ivCharacter)
                mbtIsFavorite.apply {
                    setOnClickListener { favoriteClick.invoke(character, position) }
                    mbtIsFavorite.setBackgroundResource(
                        if (character.isFavorite) R.drawable.ic_baseline_favorite
                        else R.drawable.ic_baseline_favorite_border
                    )
                }
                root.setOnClickListener { characterClick.invoke(character) }
            }
        }
    }

    class CharacterDiffCallBack : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
            oldItem == newItem
    }
}