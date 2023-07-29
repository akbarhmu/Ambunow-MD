package braincore.megalogic.ambunow.presentation.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import braincore.megalogic.ambunow.databinding.ActivitySplashScreenBinding
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
}