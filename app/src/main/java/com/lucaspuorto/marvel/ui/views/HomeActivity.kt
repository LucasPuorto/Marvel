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
import androidx.lifecycle.Observer
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
import com.lucaspuorto.marvel.presentation.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private val loading: ShimmerFrameLayout by lazy { findViewById(R.id.shimmerLoading) }
    private val group: Group by lazy { findViewById(R.id.group) }
    private val comicsErrorView: LinearLayout by lazy { findViewById(R.id.comicsErrorView) }
    private val tilSearchContainer: TextInputLayout by lazy { findViewById(R.id.tilSearchContainer) }
    private val tieSearch: TextInputEditText by lazy { findViewById(R.id.tieSearch) }
    private val characterName: TextView by lazy { findViewById(R.id.tvCharacterName) }
    private val characterImage: ImageView by lazy { findViewById(R.id.ivCharacterImage) }
    private val characterDescription: TextView by lazy { findViewById(R.id.tvCharacterDescription) }
    private val comicsList: RecyclerView by lazy { findViewById(R.id.rvComics) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupObserves()
        initView()
    }

    private fun initView() {
        setupCharacterSearch()
    }

    private fun setupCharacterSearch() {
        tieSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val inputText = tilSearchContainer.editText?.text.toString()
                    viewModel.fetchCharacter(inputText)
                }
            }
            false
        }
    }

    private fun setupObserves() {
        viewModel.characterLiveData.observe(this@HomeActivity, Observer { state -> onGetCharacterResponse(state) })
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
        loading.visibility = View.VISIBLE
        group.visibility = View.GONE
        comicsErrorView.visibility = View.GONE
    }

    private fun onGetCharacterSuccess(data: CharacterViewData) {
        loading.visibility = View.GONE
        group.visibility = View.VISIBLE
        comicsErrorView.visibility = View.GONE

        characterName.text = data.characterName
        Glide.with(this@HomeActivity)
            .load(data.characterImageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(characterImage)
        characterDescription.text = data.characterDescription
    }

    private fun onGetCharacterError() {
        loading.visibility = View.GONE
        group.visibility = View.GONE
        comicsErrorView.visibility = View.VISIBLE
    }
}