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
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.viewmodel.CharacterDetailsViewModel
import com.lucaspuorto.marvel.viewmodel.uistate.FavoriteButtonUiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding

    private val viewModel: CharacterDetailsViewModel by viewModel()

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

        setupObserve()
        setupViews()
    }

    private fun setupObserve() {
        viewModel.favoriteLiveData.observe(this) { favoriteState ->
            handlerFavoriteState(favoriteState)
        }
    }

    private fun handlerFavoriteState(favoriteState: FavoriteButtonUiState) {
        when (favoriteState) {
            FavoriteButtonUiState.IsFavorite -> {
                binding.mbtIsFavorite.apply {
                    contentDescription = getString(R.string.adding_as_favorite)
                    setBackgroundResource(R.drawable.ic_baseline_favorite)
                }
            }
            FavoriteButtonUiState.NotFavorite -> {
                binding.mbtIsFavorite.apply {
                    contentDescription = getString(R.string.removig_as_favorite)
                    setBackgroundResource(R.drawable.ic_baseline_favorite_border)
                }
            }
        }
    }

    private fun setupViews() {
        characterModel?.let { character ->
            setupName(character)
            setupImage(character)
            setupDescription(character)
            setupFavoriteButton(character)
        }
        binding.mbtBack.setOnClickListener {
            finish()
        }
    }

    private fun setupName(character: CharacterModel) {
        binding.tvCharacterName.text = character.name
    }

    private fun setupImage(character: CharacterModel) {
        Glide.with(binding.root.context)
            .load(character.thumbnail)
            .placeholder(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.ivCharacterImage)
    }

    private fun setupDescription(character: CharacterModel) {
        binding.tvCharacterDescription.text = character.description
    }

    private fun setupFavoriteButton(character: CharacterModel) {
        viewModel.setFavoriteButtonState(character.isFavorite)
        binding.mbtIsFavorite.setOnClickListener { viewModel.favoriteStateChange(character) }
    }
}