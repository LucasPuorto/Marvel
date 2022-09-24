package com.lucaspuorto.marvel.view

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.databinding.ActivityCharctersBinding
import com.lucaspuorto.marvel.viewmodel.CharactersViewModel
import com.lucaspuorto.marvel.viewmodel.uistate.CharactersUiState
import com.lucaspuorto.marvel.viewmodel.uistate.LoadingUiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharctersBinding

    private val viewModel: CharactersViewModel by viewModel()

    private val adapter = CharactersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharctersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserve()
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.character_menu, menu)
        setupCharacterSearch(menu)
        setupFavoritesButton(menu)
        return super.onCreateOptionsMenu(menu)
    }

    //TODO: Remove Toasts
    private fun setupObserve() {
        viewModel.run {
            loadingLiveData.observe(this@CharactersActivity) { loadingState ->
                when (loadingState) {
                    LoadingUiState.Show -> Toast.makeText(this@CharactersActivity, "Show Loading", Toast.LENGTH_LONG).show()
                    LoadingUiState.Hide -> Toast.makeText(this@CharactersActivity, "Hide Loading", Toast.LENGTH_LONG).show()
                }
            }

            charactersLiveData.observe(this@CharactersActivity) {
                when (it) {
                    is CharactersUiState.Success -> adapter.submitList(it.charactersList)
                    CharactersUiState.Error -> Toast.makeText(this@CharactersActivity, "Erro", Toast.LENGTH_LONG).show()
                }
            }
        }
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