package braincore.megalogic.ambunow.utils.ext

import android.content.Context
import androidx.annotation.IdRes
import braincore.megalogic.ambunow.R
import braincore.megalogic.ambunow.exception.InvalidCredentialsException
import braincore.megalogic.ambunow.exception.InvalidUserException
import braincore.megalogic.ambunow.exception.NoInternetConnectionException

fun Context.getErrorMessage(exception: Exception): String {
    return when (exception) {
        is NoInternetConnectionException -> getString(R.string.message_error_no_internet)
        is InvalidCredentialsException -> getString(R.string.message_error_invalid_credentials)
        is InvalidUserException -> getString(R.string.message_error_invalid_user)
        else -> getString(R.string.message_error_unknown)
    }
}

@IdRes
fun getErrorAnimation(exception: Exception): Int {
    return when (exception) {
        is NoInternetConnectionException -> R.raw.animation_no_internet
        is InvalidCredentialsException -> R.raw.animation_error_authentication
        is InvalidUserException -> R.raw.animation_error_authentication
        else -> R.raw.animation_error_unknown
    }
}