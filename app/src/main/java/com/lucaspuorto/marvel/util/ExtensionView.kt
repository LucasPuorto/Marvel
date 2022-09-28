package com.lucaspuorto.marvel.util

import android.os.SystemClock
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

fun View.clickWithDebounce(debounceTime: Long = 1500L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}