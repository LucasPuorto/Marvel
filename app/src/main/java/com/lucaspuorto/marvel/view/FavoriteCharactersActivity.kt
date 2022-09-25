package com.lucaspuorto.marvel.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucaspuorto.marvel.databinding.ActivityFavoriteCharactersBinding

class FavoriteCharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteCharactersBinding

    private val adapter = FavoriteCharactersAdapter()


    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, FavoriteCharactersActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteCharacter.adapter = adapter
    }
}
