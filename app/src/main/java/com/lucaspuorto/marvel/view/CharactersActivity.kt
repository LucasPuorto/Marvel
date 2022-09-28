package com.lucaspuorto.marvel.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.databinding.ActivityCharactersBinding
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.util.clickWithDebounce
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

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, CharactersActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserve()
        setupRecyclerView()
        setupErrorRetryClick()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateCharactersList()
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
                    includeEmptyList.root.gone
                    includeError.root.gone
                }
            }
            LoadingUiState.Hide -> {
                binding.shimmerCharacters.apply {
                    gone
                    stopShimmer()
                }
            }
        }
    }

    private fun handlerCharacters(charactersState: CharactersUiState) {
        when (charactersState) {
            is CharactersUiState.Success -> {
                adapter.submitList(charactersState.charactersList)
                binding.rvCharacters.visible
            }
            CharactersUiState.Update -> {
                adapter.notifyDataSetChanged()
            }
            CharactersUiState.Error -> {
                setupErrorState(getString(R.string.something_error_label))
            }
            CharactersUiState.WithoutInternet -> {
                setupErrorState(getString(R.string.without_internet_error_label))
            }
        }
    }

    private fun setupErrorState(errorText: String) {
        binding.apply {
            rvCharacters.gone
            includeError.root.gone
            includeError.apply {
                root.visible
                tvEmptyList.text = errorText
            }
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
                includeEmptyList.apply {
                    root.visible
                    tvEmptyList.text = getString(R.string.search_list_empty_label)
                }
                includeError.root.gone
            } else {
                rvCharacters.visible
                includeEmptyList.root.gone
                includeError.root.gone
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCharacters.adapter = adapter
    }

    private fun setupErrorRetryClick() {
        binding.includeError.mbtErrorRetry.clickWithDebounce {
            viewModel.getCharacters()
        }
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
        favorites?.contentDescription = getString(R.string.favorites_list)
        favorites?.setOnMenuItemClickListener {
            startActivity(FavoriteCharactersActivity.getIntent(this))
            return@setOnMenuItemClickListener true
        }
    }
}