package com.lucaspuorto.marvel.view

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.lucaspuorto.marvel.R
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.character_menu, menu)
        setupCharacterSearch(menu)
        setupFavoritesButton(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupRecyclerView() {
        binding.rvCharacters.adapter = adapter
    }

    private fun setupCharacterSearch(menu: Menu?) {
        val search = menu?.findItem(R.id.app_bar_search)
        val searchView = search?.actionView as? SearchView

        searchView?.let {
            it.isSubmitButtonEnabled = false
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    //TODO: Filter character
                    return true
                }
            })
        }
    }

    private fun setupFavoritesButton(menu: Menu?) {
        val favorites = menu?.findItem(R.id.app_bar_favorites_character)

        favorites?.setOnMenuItemClickListener {
            Toast.makeText(this, "favorites click", Toast.LENGTH_LONG).show()
            return@setOnMenuItemClickListener true
        }
    }
}