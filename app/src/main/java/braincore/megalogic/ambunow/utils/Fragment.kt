package braincore.megalogic.ambunow.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideSoftKeyboard() {
    view?.let {
        it.clearFocus()
        val inputMethodManager = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.showSnackbar(message: String, rootView : View? = view?.rootView) {
    view?.let { view ->
        val snackbar = Snackbar.make(rootView ?: view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}