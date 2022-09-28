package com.lucaspuorto.marvel.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(CharactersActivity.getIntent(this))
        finish()
    }
}