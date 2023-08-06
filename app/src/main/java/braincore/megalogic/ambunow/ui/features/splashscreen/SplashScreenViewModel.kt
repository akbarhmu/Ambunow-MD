package braincore.megalogic.ambunow.ui.features.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import braincore.megalogic.ambunow.domain.SyncResult
import braincore.megalogic.ambunow.domain.SyncUserUseCase
import braincore.megalogic.ambunow.ui.wrapper.ViewResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val syncUserUseCase: SyncUserUseCase
) : ViewModel() {

    private val _syncResult : MutableStateFlow<ViewResource<SyncResult>> = MutableStateFlow(ViewResource.Loading())
    val syncResult: StateFlow<ViewResource<SyncResult>> get() = _syncResult

    fun syncUser(){
        viewModelScope.launch {
            syncUserUseCase().collect{
                _syncResult.value = it
            }
        }
    }

}