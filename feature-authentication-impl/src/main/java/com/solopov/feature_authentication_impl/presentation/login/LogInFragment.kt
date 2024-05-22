package com.solopov.feature_authentication_impl.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.solopov.common.base.BaseFragment
import com.solopov.common.data.remote.exceptions.AuthException
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import com.solopov.feature_authentication_impl.databinding.FragmentLogInBinding
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

class LogInFragment: BaseFragment<LogInViewModel>() {

    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {

        with (viewModel) {
            with(binding) {

                logInBtn.setOnClickListener {
                    emailTextInput.helperText = null
                    passwordTextInput.helperText = null
                    signIn(emailEt.text.toString(), passwordEt.text.toString())
                    logInBtn.setLoading(true)
                }

                errorsChannel.consumeAsFlow().observe { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)

                    logInBtn.setLoading(false)

                    when (error) {
                        is AuthException.NoSuchEmailException, is AuthException.InvalidEmailException -> {
                            emailTextInput.helperText = error.message
                        }

                        is AuthException.NoEmptyPasswordException, is AuthException.WrongPasswordException-> {
                            passwordTextInput.helperText = error.message
                        }

                        else ->  Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()

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
                    viewModel.goFromLogInToUserProfile()
                }
            }

            with(binding) {
                signupLnk.setOnClickListener {
                    viewModel.goToSignUp()
                }
                forgotPasswordLnk.setOnClickListener {
                    viewModel.goToPasswordRecovery()
                }
            }
        }
    }

}
