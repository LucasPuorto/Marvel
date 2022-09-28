package com.lucaspuorto.marvel.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.databinding.ItemFavoriteCharcterBinding
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.util.clickWithDebounce

class FavoriteCharactersAdapter(
    private val characterClick: (CharacterModel) -> Unit
) : ListAdapter<CharacterModel, FavoriteCharactersAdapter.FavoriteCharacterViewHolder>(FavoriteCharacterDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCharacterViewHolder =
        FavoriteCharacterViewHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteCharacterViewHolder, position: Int) {
        holder.bind(getItem(position), characterClick)
    }

    class FavoriteCharacterViewHolder(private val binding: ItemFavoriteCharcterBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): FavoriteCharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteCharcterBinding.inflate(layoutInflater, parent, false)
                return FavoriteCharacterViewHolder(binding)
            }
        }

        fun bind(character: CharacterModel, characterClick: (CharacterModel) -> Unit) {
            binding.apply {
                tvCharacter.text = character.name
                Glide.with(binding.root.context)
                    .load(character.thumbnail)
                    .placeholder(R.drawable.ic_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.ivCharacter)
                root.clickWithDebounce { characterClick.invoke(character) }
            }
        }
    }

    class FavoriteCharacterDiffCallBack : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
            oldItem == newItem
    }
}