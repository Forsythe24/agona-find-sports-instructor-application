package com.solopov.feature_authentication_impl.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.databinding.FragmentLogInBinding
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent

class LogInFragment : BaseFragment<LogInViewModel>() {

    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {

        with(binding) {
            signupLnk.setOnClickListener {
                viewModel.goToSignUp()
            }
            forgotPasswordLnk.setOnClickListener {
                viewModel.goToPasswordRecovery()
            }

            emailEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    emailTextInput.error = viewModel.validateEmail(text.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            logInBtn.setOnClickListener {
                emailTextInput.error = viewModel.validateEmail(emailEt.text.toString())
                if (emailTextInput.error == null) {
                    viewModel.signIn(emailEt.text.toString(), passwordEt.text.toString())
                    logInBtn.setLoading(true)
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
            with(binding) {
                authenticationResultFlow.observe { authenticated ->
                    if (authenticated) {
                        viewModel.goFromLogInToUserProfile()
                    }
                }

                errorMessageChannel.observe { message ->
                    logInBtn.setLoading(false)
                    showSnackbar(message, Snackbar.LENGTH_SHORT)

                }

                emailErrorTextFlow.observe { text ->
                    logInBtn.setLoading(false)
                    emailTextInput.error = text
                }

                passwordErrorTextFlow.observe { text ->
                    logInBtn.setLoading(false)
                    passwordTextInput.error = text
                }
            }
        }
    }
}
