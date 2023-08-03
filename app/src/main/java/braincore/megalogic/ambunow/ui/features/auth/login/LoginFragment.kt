package braincore.megalogic.ambunow.ui.features.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import braincore.megalogic.ambunow.data.source.Resource
import braincore.megalogic.ambunow.databinding.FragmentLoginBinding
import braincore.megalogic.ambunow.utils.hideSoftKeyboard
import braincore.megalogic.ambunow.utils.isValidEmail
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.collect {
                    handleData(it)
                }
            }
        }
    }

    private fun handleData(it: Any) {
        resetView()
        when (it) {
            is Resource.Success<*> -> {
                // TODO: Check role, Redirect to home
            }

            is Resource.Error -> {
                showError(it.message)
            }

            is Resource.Loading -> {
                showLoading(true)
            }
        }
    }

    private fun resetView() {
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
            inputEmail.isEnabled = !isLoading
            inputPassword.isEnabled = !isLoading
            btnLogin.isEnabled = !isLoading
        }
    }

    private fun initView() {
        binding.apply {
            btnLogin.setOnClickListener {
                hideSoftKeyboard()
                val email = inputEmail.editText?.text.toString()
                val password = inputPassword.editText?.text.toString()

                // Validation
                if (email.isEmpty() || password.isEmpty()) {
                    showError("Email or password cannot be empty")
                    return@setOnClickListener
                }
                if (!email.isValidEmail()) {
                    showError("Email is not valid")
                    return@setOnClickListener
                }

                viewModel.loginUser(email, password)
            }

        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}