package com.example.ghostzilla.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.postDelayed
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.ghostzilla.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.showKeyboard() {
    postDelayed(100) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun EditText.clearTextAndFocus(fragment: Fragment) {
    this.apply {
        setText("")
        clearFocus()
        fragment.hideKeyboard()
    }
}

fun ImageView.changeImageOnEdittext(editText: EditText, emptyImage : Int, nonEmpty : Int) {
    if (editText.text.toString().isEmpty())
        this.setImageResource(emptyImage)
    else
        this.setImageResource(nonEmpty)
}

@ExperimentalCoroutinesApi
fun EditText.searchQuery() = callbackFlow<Unit> {
    doAfterTextChanged { offer(Unit) }
    awaitClose()
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}
