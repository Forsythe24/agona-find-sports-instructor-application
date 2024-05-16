package com.solopov.feature_authentication_impl.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.solopov.common.base.BaseFragment
import com.solopov.common.data.remote.exceptions.AuthenticationException
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import com.solopov.feature_authentication_impl.databinding.FragmentLogInBinding
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInFragment: BaseFragment<LogInViewModel>() {

    private lateinit var binding: FragmentLogInBinding

    @Inject
    lateinit var router: AuthRouter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {
        with (viewModel) {
            with(binding) {

                signupLnk.setOnClickListener {
                    router.goToSignUpPage()
                }

                logInBtn.setOnClickListener {
                    emailTextInput.helperText = null
                    passwordTextInput.helperText = null
                    signIn(emailEt.text.toString(), passwordEt.text.toString())
                }

                lifecycleScope.launch {
                    errorsChannel.consumeEach { error ->
                        val errorMessage = error.message ?: getString(R.string.unknown_error)

                        when (error) {
                            is AuthenticationException.NoSuchEmailException, is AuthenticationException.InvalidEmailException -> {
                                emailTextInput.helperText = error.message
                            }

                            is AuthenticationException.NoEmptyPasswordException -> {
                                passwordTextInput.helperText = error.message
                            }

                            is AuthenticationException.WrongEmailOrPasswordException -> {
                                showAlert(getString(R.string.authentication_error), errorMessage)
                            }

                            else -> Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }


    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .logInComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: LogInViewModel) {
        with(viewModel) {
            authenticationResultFlow.observe {
                if (it) {
                    router.goFromLogInToUserProfile()
                }
            }

            progressBarFlow.observe { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }
    }

}
