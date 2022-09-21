package com.lucaspuorto.marvel.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucaspuorto.marvel.databinding.ActivityCharctersBinding

class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharctersBinding

    private val adapter = CharacterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharctersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvCharacters.adapter = adapter
    }
}