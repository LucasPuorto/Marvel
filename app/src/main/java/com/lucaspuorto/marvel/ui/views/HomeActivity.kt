package com.lucaspuorto.marvel.ui.views

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lucaspuorto.marvel.R
import com.lucaspuorto.marvel.presentation.StateError
import com.lucaspuorto.marvel.presentation.StateLoading
import com.lucaspuorto.marvel.presentation.StateResponse
import com.lucaspuorto.marvel.presentation.StateSuccess
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.presentation.viewdata.ComicsListViewData
import com.lucaspuorto.marvel.presentation.viewmodel.HomeViewModel
import com.lucaspuorto.marvel.presentation.viewmodel.ViewModelFactory
import com.lucaspuorto.marvel.ui.adapter.ComicsAdapter
import com.lucaspuorto.marvel.utils.INITIAL_POSITION
import com.lucaspuorto.marvel.utils.changeVisibility
import com.lucaspuorto.marvel.utils.checkConnection
import com.lucaspuorto.marvel.utils.hideKeyboard

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels { ViewModelFactory() }
    private val comicsAdapter = ComicsAdapter()

    private val loadingActivity: ShimmerFrameLayout by lazy { findViewById(R.id.shimmerLoadingActivity) }
    private val loadingComics: ShimmerFrameLayout by lazy { findViewById(R.id.shimmerLoadingComics) }
    private val characterInformation: Group by lazy { findViewById(R.id.characterInformation) }
    private val comicsError: LinearLayout by lazy { findViewById(R.id.comicsErrorView) }
    private val characterError: TextView by lazy { findViewById(R.id.tvCharacterError) }
    private val tilSearchContainer: TextInputLayout by lazy { findViewById(R.id.tilSearchContainer) }
    private val tieSearch: TextInputEditText by lazy { findViewById(R.id.tieSearch) }
    private val characterName: TextView by lazy { findViewById(R.id.tvCharacterName) }
    private val characterImage: ImageView by lazy { findViewById(R.id.ivCharacterImage) }
    private val characterDescription: TextView by lazy { findViewById(R.id.tvCharacterDescription) }
    private val comicsList: RecyclerView by lazy { findViewById(R.id.rvComics) }
    private val snackBarView: View by lazy { findViewById(R.id.snack_bar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
    }

    private fun initView() {
        setupRecyclerView()
        setupCharacterSearch()
    }

    private fun setupRecyclerView() {
        comicsList.adapter = comicsAdapter
    }

    private fun setupCharacterSearch() {
        tieSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> checkConnection(this@HomeActivity, snackBarView, resources) {
                    val inputText = tilSearchContainer.editText?.text.toString()
                    whenHaveConnectionCallCharacter(inputText)
                    hideKeyboard()
                }
            }
            false
        }
    }

    private fun whenHaveConnectionCallCharacter(inputText: String) {
        viewModel.getCharacter(inputText).observe(this@HomeActivity, { state -> onGetCharacterResponse(state) })
    }

    private fun onGetCharacterResponse(state: StateResponse<CharacterViewData>) {
        state.let {
            when (state) {
                is StateLoading -> onGetCharacterLoading()
                is StateSuccess -> onGetCharacterSuccess(state.data)
                is StateError -> onGetCharacterError()
            }
        }
    }

    private fun onGetCharacterLoading() {
        setupCharacterVisibility(loadingActivityVisibility = true)
    }

    private fun onGetCharacterSuccess(data: CharacterViewData) {
        setupCharacterVisibility(characterInformationVisibility = true)

        setupCharacterName(data)
        setupCharacterImage(data)
        setupCharacterDescription(data)
        checkConnection(this@HomeActivity, snackBarView, resources) {
            whenHaveConnectionCallComicsList(data)
        }
    }

    private fun setupCharacterName(data: CharacterViewData) {
        characterName.text = data.characterName
    }

    private fun setupCharacterImage(data: CharacterViewData) {
        Glide.with(this@HomeActivity)
            .load(data.characterImageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(characterImage)
    }

    private fun setupCharacterDescription(data: CharacterViewData) {
        characterDescription.text = data.characterDescription
    }

    private fun whenHaveConnectionCallComicsList(data: CharacterViewData) {
        viewModel.getComics(data.characterId).observe(this@HomeActivity, { state -> onGetComicsListResponse(state) })
    }

    private fun onGetCharacterError() {
        setupCharacterVisibility(characterErrorVisibility = true)
    }

    private fun setupCharacterVisibility(
        loadingActivityVisibility: Boolean = false,
        characterInformationVisibility: Boolean = false,
        characterErrorVisibility: Boolean = false
    ) {
        loadingActivity.changeVisibility(loadingActivityVisibility)
        characterInformation.changeVisibility(characterInformationVisibility)
        characterError.changeVisibility(characterErrorVisibility)
    }

    private fun onGetComicsListResponse(state: StateResponse<List<ComicsListViewData>>) {
        state.let {
            when (state) {
                is StateLoading -> onGetComicsListLoading()
                is StateSuccess -> onGetComicsListSuccess(state.data)
                is StateError -> onGetComicsListError()
            }
        }
    }

    private fun onGetComicsListLoading() {
        setupComicsVisibility(loadingComicsVisibility = true)
    }

    private fun onGetComicsListSuccess(data: List<ComicsListViewData>) {
        setupComicsVisibility(comicsListVisibility = true)
        comicsAdapter.run {
            addComics(data)
            notifyDataSetChanged()
        }
        comicsList.smoothScrollToPosition(INITIAL_POSITION)
    }

    private fun onGetComicsListError() {
        setupComicsVisibility(comicsErrorVisibility = true)
    }

    private fun setupComicsVisibility(
        loadingComicsVisibility: Boolean = false,
        comicsListVisibility: Boolean = false,
        comicsErrorVisibility: Boolean = false
    ) {
        loadingComics.changeVisibility(loadingComicsVisibility)
        comicsList.changeVisibility(comicsListVisibility)
        comicsError.changeVisibility(comicsErrorVisibility)
    }
}
