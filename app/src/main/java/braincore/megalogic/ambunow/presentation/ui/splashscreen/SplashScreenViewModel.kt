package braincore.megalogic.ambunow.presentation.ui.splashscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import braincore.megalogic.core.data.repository.UserRepository
import braincore.megalogic.core.data.source.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val _splashScreenState = MutableStateFlow<SplashScreenState>(SplashScreenState.Loading)
    val splashScreenState: StateFlow<SplashScreenState> get() = _splashScreenState

    init {
        getIsUserLoggedIn()
    }

    private fun getIsUserLoggedIn() {
        viewModelScope.launch {
            when (val result = userRepository.isUserLoggedIn().first()) {
                is Resource.Success -> {
                    if (result.data) {
                        // TODO: Sync to local, Check user role, Navigate to main
                    } else {
                        userRepository.logout()
                        _splashScreenState.update {
                            SplashScreenState.NavigateToLogin
                        }
                    }
                }
                is Resource.Error -> {
                    _splashScreenState.update {
                        SplashScreenState.Error
                    }
                    Log.d("Get Is User Logged In", "Error: ${result.message}")
                }
                else -> {}
            }
        }
    }
}