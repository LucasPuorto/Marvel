package com.lucaspuorto.marvel.util

import android.view.View

val View.visible: View
    get() {
        visibility = View.VISIBLE
        return this
    }

val View.gone: View
    get() {
        visibility = View.GONE
        return this
    }
