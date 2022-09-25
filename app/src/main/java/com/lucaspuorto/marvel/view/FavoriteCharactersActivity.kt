package com.lucaspuorto.marvel.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lucaspuorto.marvel.databinding.ActivityFavoriteCharactersBinding
import com.lucaspuorto.marvel.viewmodel.FavoriteCharactersViewModel
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
        setupRecyclerView()
    }

    private fun setupObserve() {
        viewModel.allFavoritesLiveData.observe(this) {
            Toast.makeText(this, it.size.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteCharacter.adapter = adapter
    }
}
