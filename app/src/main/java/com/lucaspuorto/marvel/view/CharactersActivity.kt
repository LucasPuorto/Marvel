package com.lucaspuorto.marvel.view

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.databinding.ActivityCharactersBinding
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.util.gone
import com.lucaspuorto.marvel.util.visible
import com.lucaspuorto.marvel.viewmodel.CharactersViewModel
import com.lucaspuorto.marvel.viewmodel.uistate.CharactersUiState
import com.lucaspuorto.marvel.viewmodel.uistate.LoadingUiState
import com.lucaspuorto.marvel.viewmodel.uistate.SearchCharacterUiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersBinding

    private val viewModel: CharactersViewModel by viewModel()

    private val adapter = CharactersAdapter(
        characterClick = { character -> startActivity(CharacterDetailsActivity.getIntent(this, character)) },
        favoriteClick = { character, position -> favoriteClick(character, position) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserve()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateCharactersList()
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.character_menu, menu)
        setupCharacterSearch(menu)
        setupFavoritesButton(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun favoriteClick(character: CharacterModel, position: Int) {
        viewModel.favoriteStateChange(character)
        adapter.notifyItemChanged(position)
    }

    private fun setupObserve() {
        viewModel.run {
            loadingLiveData.observe(this@CharactersActivity) { loadingState ->
                handlerLoading(loadingState)
            }

            charactersLiveData.observe(this@CharactersActivity) { charactersState ->
                handlerCharacters(charactersState)
            }

            searchLiveData.observe(this@CharactersActivity) { charactersList ->
                handlerSearchCharacter(charactersList)
            }
        }
    }

    private fun handlerLoading(loadingState: LoadingUiState) {
        when (loadingState) {
            LoadingUiState.Show -> {
                binding.apply {
                    rvCharacters.gone
                    shimmerCharacters.apply {
                        visible
                        startShimmer()
                    }
                }
            }
            LoadingUiState.Hide -> {
                binding.apply {
                    rvCharacters.visible
                    shimmerCharacters.apply {
                        gone
                        stopShimmer()
                    }
                }
            }
        }
    }

    //TODO: Remove Toast
    private fun handlerCharacters(charactersState: CharactersUiState) {
        when (charactersState) {
            is CharactersUiState.Success -> {
                adapter.submitList(charactersState.charactersList)
            }
            CharactersUiState.Error -> Toast.makeText(this@CharactersActivity, "Erro", Toast.LENGTH_LONG).show()
        }
    }

    private fun handlerSearchCharacter(charactersState: SearchCharacterUiState) {
        when (charactersState) {
            is SearchCharacterUiState.HasMatch -> {
                adapter.submitList(charactersState.charactersList)
                showEmptyCharactersList(false)
            }
            SearchCharacterUiState.HasNoMatch -> {
                showEmptyCharactersList(true)
            }
            is SearchCharacterUiState.MinCharsUnreached -> {
                adapter.submitList(charactersState.charactersList)
                showEmptyCharactersList(false)
            }
        }
    }

    private fun showEmptyCharactersList(show: Boolean) {
        binding.apply {
            if (show) {
                rvCharacters.gone
                includeEmptyList.root.visible
            } else {
                rvCharacters.visible
                includeEmptyList.root.gone
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
                    viewModel.searchFilter(newText)
                    return true
                }
            })
        }
    }

    private fun setupFavoritesButton(menu: Menu?) {
        val favorites = menu?.findItem(R.id.app_bar_favorites_character)

        favorites?.setOnMenuItemClickListener {
            startActivity(FavoriteCharactersActivity.getIntent(this))
            return@setOnMenuItemClickListener true
        }
    }
}