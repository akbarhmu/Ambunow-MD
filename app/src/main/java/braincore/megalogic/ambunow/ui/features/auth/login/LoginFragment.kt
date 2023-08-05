package braincore.megalogic.ambunow.ui.features.auth.login

import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import braincore.megalogic.ambunow.R
import braincore.megalogic.ambunow.base.BaseFragment
import braincore.megalogic.ambunow.constant.LoginFieldConstants
import braincore.megalogic.ambunow.databinding.FragmentLoginBinding
import braincore.megalogic.ambunow.exception.FieldErrorException
import braincore.megalogic.ambunow.utils.ext.getErrorAnimation
import braincore.megalogic.ambunow.utils.ext.getErrorMessage
import braincore.megalogic.ambunow.utils.ext.subscribe
import braincore.megalogic.ambunow.utils.spanTextPrimary
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel: LoginViewModel by viewModel()

    override fun initView() {
        binding.apply {
            spanTextPrimary(
                textView = tvRegister,
                text = getString(R.string.text_login_no_account),
                start = 18,
                bold = true
            )
            btnLogin.setOnClickListener {
                viewModel.loginUser(
                    inputEmail.editText?.text.toString(),
                    inputPassword.editText?.text.toString()
                )
            }
        }
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loginResult.collect { loginResult ->
                    resetField()
                    loginResult.subscribe(
                        doOnSuccess = {
                            showLoading(false)
                        },
                        doOnError = {
                            showLoading(false)
                            if (loginResult.exception is FieldErrorException) {
                                handleFieldError(loginResult.exception)
                            } else {
                                loginResult.exception?.let { e ->
                                    val action = LoginFragmentDirections.actionLoginFragmentToErrorBottomSheetFragment(
                                        requireContext().getErrorMessage(e),
                                        getErrorAnimation(e)
                                    )
                                    findNavController().navigate(action)
                                }
                            }
                        },
                        doOnLoading = {
                            showLoading(true)
                        }
                    )
                }
            }
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == LoginFieldConstants.FIELD_EMAIL) {
                binding.inputEmail.error = getString(errorField.second)
            }
            if (errorField.first == LoginFieldConstants.FIELD_PASSWORD) {
                binding.inputPassword.endIconMode = TextInputLayout.END_ICON_NONE
                binding.inputPassword.error = getString(errorField.second)
            }
        }
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.progressBar.isVisible = isShowLoading
    }

    private fun resetField() {
        showLoading(false)
        binding.inputPassword.isErrorEnabled = false
        binding.inputEmail.isErrorEnabled = false
        binding.inputPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
    }

}