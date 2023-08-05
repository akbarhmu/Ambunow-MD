package braincore.megalogic.ambunow.utils

import android.text.TextUtils
import android.util.Patterns

object StringUtils {
    fun isEmailValid(input : CharSequence?) : Boolean{
        return if(TextUtils.isEmpty(input)){
            false
        }else{
            Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }
    }
}