package braincore.megalogic.ambunow.ui.features.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import braincore.megalogic.ambunow.domain.LoginUserUseCase
import braincore.megalogic.ambunow.ui.viewparam.UserViewParam
import braincore.megalogic.ambunow.ui.wrapper.ViewResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _loginResult: MutableStateFlow<ViewResource<UserViewParam?>> =
        MutableStateFlow(ViewResource.Empty())
    val loginResult: StateFlow<ViewResource<UserViewParam?>> get() = _loginResult

    fun loginUser(email: String, password: String) {
        Log.e("LoginViewModel", "loginUser: $email, $password")
        viewModelScope.launch {
            loginUserUseCase(LoginUserUseCase.Param(email, password)).collect {
                _loginResult.value = it
            }
        }
    }
}