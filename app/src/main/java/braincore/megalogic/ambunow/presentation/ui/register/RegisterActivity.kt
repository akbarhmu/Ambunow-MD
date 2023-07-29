package braincore.megalogic.ambunow.presentation.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import braincore.megalogic.ambunow.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppBar()
    }

    private fun setupAppBar() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}