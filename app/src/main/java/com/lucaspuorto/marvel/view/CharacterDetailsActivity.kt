package com.lucaspuorto.marvel.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.databinding.ActivityCharacterDetailsBinding
import com.lucaspuorto.marvel.model.CharacterModel

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding

    companion object {
        private const val CHARACTER_KEY = "character_key"

        fun getIntent(context: Context, characterModel: CharacterModel): Intent =
            Intent(context, CharacterDetailsActivity::class.java)
                .putExtra(CHARACTER_KEY, characterModel)
    }

    private val characterModel: CharacterModel? by lazy {
        if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(CHARACTER_KEY, CharacterModel::class.java)
        } else {
            intent.getParcelableExtra(CHARACTER_KEY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterModel?.let {

            binding.tvCharacterName.text = it.name

            Glide.with(binding.root.context)
                .load(it.thumbnail)
                .placeholder(R.drawable.ic_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivCharacterImage)

            binding.tvCharacterDescription.text = it.description
        }
    }
}