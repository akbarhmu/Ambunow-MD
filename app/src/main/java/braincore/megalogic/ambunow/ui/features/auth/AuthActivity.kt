package braincore.megalogic.ambunow.ui.features.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import braincore.megalogic.ambunow.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}