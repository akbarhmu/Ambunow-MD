package braincore.megalogic.ambunow.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import braincore.megalogic.ambunow.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideSoftKeyboard() {
    view?.let {
        it.clearFocus()
        val inputMethodManager =
            it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.showSnackbar(message: String, rootView: View? = view?.rootView) {
    view?.let { view ->
        val snackbar = Snackbar.make(rootView ?: view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}

fun Fragment.spanTextPrimary(
    textView: TextView,
    text: String,
    start: Int,
    end: Int? = null,
    bold: Boolean = false
) {
    textView.setText(text, TextView.BufferType.SPANNABLE)
    val spannable = textView.text as Spannable
    val spanEnd = end ?: spannable.length

    val color =
        if (requireContext().resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            requireContext().getColor(R.color.md_theme_dark_primary)
        } else {
            requireContext().getColor(R.color.md_theme_light_primary)
        }

    spannable.setSpan(
        ForegroundColorSpan(color), start, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE
    )
    if (bold) {
        spannable.setSpan(
            StyleSpan(Typeface.BOLD), start, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}