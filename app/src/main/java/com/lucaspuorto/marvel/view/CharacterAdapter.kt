package com.lucaspuorto.marvel.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucaspuorto.marvel.databinding.ItemCharcterBinding

class CharacterAdapter : ListAdapter<String, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder.from(parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CharacterViewHolder(private val binding: ItemCharcterBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharcterBinding.inflate(layoutInflater, parent, false)
                return CharacterViewHolder(binding)
            }
        }

        fun bind(character: String) {
            binding.tvCharacter.text = character
        }
    }

    class CharacterDiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}