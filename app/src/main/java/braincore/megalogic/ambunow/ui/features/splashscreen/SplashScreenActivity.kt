package braincore.megalogic.ambunow.ui.features.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import braincore.megalogic.ambunow.base.BaseActivity
import braincore.megalogic.ambunow.constant.Role
import braincore.megalogic.ambunow.databinding.ActivitySplashScreenBinding
import braincore.megalogic.ambunow.ui.features.auth.AuthActivity
import braincore.megalogic.ambunow.utils.ext.subscribe
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>(ActivitySplashScreenBinding::inflate) {

    override val viewModel: SplashScreenViewModel by viewModel()

    override fun initView() {
        viewModel.syncUser()
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.syncResult.collect {
                    it.subscribe(doOnSuccess = { response ->
                        if (response.payload?.first == true) {
                            when (response.payload.second?.role) {
                                Role.ADMIN -> {}
                                Role.DRIVER -> {}
                                else -> {}
                            }
                        } else {
                            lifecycleScope.launch { navigateToLogin() }
                        }
                    }, doOnError = { error ->
                        Toast.makeText(
                            this@SplashScreenActivity,
                            error.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
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