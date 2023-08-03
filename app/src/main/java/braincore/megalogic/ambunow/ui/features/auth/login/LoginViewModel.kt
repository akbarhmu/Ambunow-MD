package braincore.megalogic.ambunow.ui.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import braincore.megalogic.ambunow.data.repository.UserRepository
import braincore.megalogic.ambunow.data.source.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    val loginResult: MutableStateFlow<Resource<FirebaseUser>> = MutableStateFlow(Resource.Empty)

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.login(email, password).collect {
                loginResult.value = it
            }
        }
    }
}