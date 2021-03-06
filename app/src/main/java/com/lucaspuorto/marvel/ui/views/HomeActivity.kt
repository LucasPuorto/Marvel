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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

    private val swipeRefresh: SwipeRefreshLayout by lazy { findViewById(R.id.swipeRefresh) }
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

    private var character: String = ""
    private var characterId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
    }

    private fun initView() {
        setupRecyclerView()
        setupSwipeRefresh()
        setupComicsErrorClickListener()
        setupCharacterSearch()
    }

    private fun setupRecyclerView() {
        comicsList.adapter = comicsAdapter
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.apply {
            isEnabled = character.isNotEmpty()
            setColorSchemeResources(R.color.red_900)
            setOnRefreshListener {
                checkConnection(this@HomeActivity, snackBarView, resources) { whenHaveConnectionCallCharacter() }
            }
        }
    }

    private fun setupComicsErrorClickListener() {
        comicsError.setOnClickListener {
            checkConnection(this@HomeActivity, snackBarView, resources) { whenHaveConnectionCallComicsList() }
        }
    }

    private fun setupCharacterSearch() {
        tieSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard()
                    character = tilSearchContainer.editText?.text.toString()
                    swipeRefresh.isEnabled = character.isNotEmpty()
                    checkConnection(this@HomeActivity, snackBarView, resources) { whenHaveConnectionCallCharacter() }
                }
            }
            false
        }
    }

    private fun whenHaveConnectionCallCharacter() {
        viewModel.getCharacter(character).observe(this@HomeActivity, { state -> onGetCharacterResponse(state) })
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
        swipeRefresh.isRefreshing = false
        setupCharacterVisibility(loadingActivityVisibility = true)
    }

    private fun onGetCharacterSuccess(data: CharacterViewData) {
        setupCharacterVisibility(characterInformationVisibility = true)
        setupCharacterInformation(data)
        characterId = data.characterId
        checkConnection(this@HomeActivity, snackBarView, resources) { whenHaveConnectionCallComicsList() }
    }

    private fun setupCharacterInformation(data: CharacterViewData) {
        characterName.text = data.characterName
        setupCharacterImage(data)
        characterDescription.text = data.characterDescription
    }

    private fun setupCharacterImage(data: CharacterViewData) {
        Glide.with(this@HomeActivity)
            .load(data.characterImageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(characterImage)
    }

    private fun whenHaveConnectionCallComicsList() {
        viewModel.getComics(characterId).observe(this@HomeActivity, { state -> onGetComicsListResponse(state) })
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
