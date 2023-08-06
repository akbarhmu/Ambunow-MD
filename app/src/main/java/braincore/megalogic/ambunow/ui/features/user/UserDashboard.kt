package braincore.megalogic.ambunow.ui.features.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import braincore.megalogic.ambunow.databinding.ActivityUserDashboardBinding

class UserDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityUserDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}