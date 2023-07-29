package braincore.megalogic.ambunow.presentation.ui.splashscreen

sealed class SplashScreenState {
    data object Loading: SplashScreenState()
    data object Error : SplashScreenState()
    data object NavigateToLogin: SplashScreenState()
    data object NavigateToUserMain: SplashScreenState()
    data object NavigateToDriverMain: SplashScreenState()
}