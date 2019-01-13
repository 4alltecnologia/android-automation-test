package com.fourall.aat.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.isKeyboardOpened(): Boolean {
    val inm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inm.isAcceptingText
}

fun Activity.closeKeyboard(view: View) {
    val inm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inm.hideSoftInputFromWindow(view.windowToken, 0)
}