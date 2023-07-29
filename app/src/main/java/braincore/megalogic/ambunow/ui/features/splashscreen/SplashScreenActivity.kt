package braincore.megalogic.ambunow.ui.features.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import braincore.megalogic.ambunow.databinding.ActivitySplashScreenBinding
import braincore.megalogic.ambunow.ui.features.auth.AuthActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.splashScreenState.collect {
                    when (it) {
                        is SplashScreenState.Error -> {

                        }

                        is SplashScreenState.NavigateToLogin -> {
                            navigateToLogin()
                        }

                        is SplashScreenState.NavigateToUserMain -> {

                        }

                        is SplashScreenState.NavigateToDriverMain -> {

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private suspend fun navigateToLogin() {
        delay(SPLASH_DELAY)
        val intent = Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    companion object {
        private const val SPLASH_DELAY = 2000L
    }
}