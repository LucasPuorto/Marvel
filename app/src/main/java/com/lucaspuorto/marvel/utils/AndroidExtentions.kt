package com.lucaspuorto.marvel.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.lucaspuorto.marvel.R

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun checkConnection(contextView: Activity, snackBarView: View, resources: Resources, successfulAction: () -> Unit) {
    if (checkNetworkConnection(contextView)) {
        successfulAction()
    } else {
        val snackBar: Snackbar = Snackbar.make(snackBarView, R.string.error_network, Snackbar.LENGTH_LONG)
        val snackBarLayout = snackBar.view
        val textView = snackBarLayout.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error_outline, NONE, NONE, NONE)
        textView.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.snack_bar_icon_padding)
        snackBar.show()
    }
}

private fun checkNetworkConnection(contextView: Activity): Boolean {
    val cm = contextView.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}