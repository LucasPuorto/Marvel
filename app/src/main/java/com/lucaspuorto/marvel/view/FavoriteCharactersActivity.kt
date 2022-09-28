package com.lucaspuorto.marvel.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.databinding.ActivityFavoriteCharactersBinding
import com.lucaspuorto.marvel.util.clickWithDebounce
import com.lucaspuorto.marvel.util.gone
import com.lucaspuorto.marvel.util.visible
import com.lucaspuorto.marvel.viewmodel.FavoriteCharactersViewModel
import com.lucaspuorto.marvel.viewmodel.uistate.FavoritesUiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteCharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteCharactersBinding

    private val viewModel: FavoriteCharactersViewModel by viewModel()

    private val adapter = FavoriteCharactersAdapter() { character ->
        startActivity(CharacterDetailsActivity.getIntent(this, character))
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, FavoriteCharactersActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserve()
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFavorites()
    }

    private fun setupObserve() {
        viewModel.allFavoritesLiveData.observe(this) { favoriteCharacters ->
            handlerFavoritesCharacters(favoriteCharacters)
        }
    }

    private fun handlerFavoritesCharacters(favoriteCharacters: FavoritesUiState) {
        when (favoriteCharacters) {
            is FavoritesUiState.HasFavorites -> {
                binding.rvFavoriteCharacter.visible
                binding.includeEmptyList.root.gone
                adapter.submitList(favoriteCharacters.favorites)
            }
            FavoritesUiState.HasNoFavorites -> {
                binding.rvFavoriteCharacter.gone
                binding.includeEmptyList.apply {
                    root.visible
                    tvEmptyList.text = getString(R.string.favorites_list_empty_label)
                }
            }
        }
    }

    private fun setupViews() {
        setupRecyclerView()
        binding.mbtBack.clickWithDebounce { finish() }
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteCharacter.adapter = adapter
    }
}
