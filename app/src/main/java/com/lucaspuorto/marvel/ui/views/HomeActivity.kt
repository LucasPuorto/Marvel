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
import com.lucaspuorto.marvel.ui.adapter.ComicsAdapter
import com.lucaspuorto.marvel.utils.INITIAL_POSITION
import com.lucaspuorto.marvel.utils.changeVisibility
import com.lucaspuorto.marvel.utils.checkConnection
import com.lucaspuorto.marvel.utils.hideKeyboard


class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private val comicsAdapter = ComicsAdapter()

    private val loadingActivity: ShimmerFrameLayout by lazy { findViewById(R.id.shimmerLoadingActivity) }
    private val loadingComics: ShimmerFrameLayout by lazy { findViewById(R.id.shimmerLoadingComics) }
    private val group: Group by lazy { findViewById(R.id.group) }
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
        setupObserves()
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
                    viewModel.fetchCharacter(inputText)
                    hideKeyboard()
                }
            }
            false
        }
    }

    private fun setupObserves() {
        viewModel.characterLiveData.observe(this@HomeActivity, { state -> onGetCharacterResponse(state) })
        viewModel.comicsListLiveData.observe(this@HomeActivity, { state -> onGetComicsListResponse(state) })
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
        loadingActivity.changeVisibility(true)
        group.changeVisibility(false)
        characterError.changeVisibility(false)
        loadingComics.changeVisibility(false)
    }

    private fun onGetCharacterSuccess(data: CharacterViewData) {
        loadingActivity.changeVisibility(false)
        group.changeVisibility(true)
        characterError.changeVisibility(false)
        loadingComics.changeVisibility(false)

        characterName.text = data.characterName
        Glide.with(this@HomeActivity)
            .load(data.characterImageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(characterImage)
        characterDescription.text = data.characterDescription
        checkConnection(this@HomeActivity, snackBarView, resources) { viewModel.fetchComics(data.characterId) }
    }

    private fun onGetCharacterError() {
        loadingActivity.changeVisibility(false)
        group.changeVisibility(false)
        characterError.changeVisibility(true)
        loadingComics.changeVisibility(false)
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
        comicsError.changeVisibility(false)
        comicsList.changeVisibility(false)
        loadingComics.changeVisibility(true)
    }

    private fun onGetComicsListSuccess(data: List<ComicsListViewData>) {
        comicsError.changeVisibility(false)
        comicsList.changeVisibility(true)
        loadingComics.changeVisibility(false)
        comicsAdapter.run {
            addComics(data)
            notifyDataSetChanged()
        }
        comicsList.scrollToPosition(INITIAL_POSITION)
    }

    private fun onGetComicsListError() {
        comicsError.changeVisibility(true)
        comicsList.changeVisibility(false)
        loadingComics.changeVisibility(false)
    }
}
