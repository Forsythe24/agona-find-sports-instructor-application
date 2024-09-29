package com.solopov.feature_authentication_impl.presentation.signup

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
import com.solopov.feature_authentication_impl.R
import com.solopov.feature_authentication_impl.databinding.FragmentSignUpBinding
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent

class SignUpFragment : BaseFragment<SignUpViewModel>() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {
        with(binding) {
            backBtn.setOnClickListener {
                viewModel.goBack()
            }

            finishSignUpBtn.setOnClickListener {
                if (isValidForm()) {
                    viewModel.createUser(
                        email = emailEt.text.toString(),
                        password = passwordEt.text.toString(),
                        name = nameEt.text.toString(),
                        age = ageEt.text.toString().toInt(),
                        gender = if (maleRb.isChecked) getString(R.string.male_gender) else getString(
                            R.string.female_gender
                        )
                    )

                } else {
                    showInvalidFormAlert()
                }

            }

            addTextChangeListeners()
        }
    }

    private fun addTextChangeListeners() {
        with(binding) {
            emailEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    emailTextInput.error = viewModel.validateEmail(text.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            passwordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    passwordTextInput.error = viewModel.validatePassword(text.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            nameEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    nameTextInput.error = viewModel.validateName(text.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            ageEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    ageTextInput.error = viewModel.validateAge(ageEt.text.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun showInvalidFormAlert() {
        var message = ""
        with(binding) {
            if (emailTextInput.error != null)
                message += getString(R.string.alert_email) + emailTextInput.error

            if (passwordTextInput.error != null)
                message += getString(R.string.alert_password) + passwordTextInput.error

            if (nameTextInput.error != null)
                message += getString(R.string.alert_name) + nameTextInput.error

            if (ageTextInput.error != null)
                message += getString(R.string.alert_age) + ageTextInput.error
        }

        showAlert(getString(R.string.invalid_form), message)
    }

    private fun isValidForm(): Boolean {
        with(binding) {
            emailTextInput.error = viewModel.validateEmail(emailEt.text.toString())
            passwordTextInput.error = viewModel.validatePassword(passwordEt.text.toString())
            nameTextInput.error = viewModel.validateName(nameEt.text.toString())
            ageTextInput.error = viewModel.validateAge(ageEt.text.toString())

            return emailTextInput.error == null && passwordTextInput.error == null && nameTextInput.error == null && ageTextInput.error == null
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .signUpComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: SignUpViewModel) {
        with(viewModel) {
            progressBarFlow.observe { isLoading ->
                binding.finishSignUpBtn.setLoading(isLoading)
            }

            errorMessageChannel.observe { message ->
                when (message) {
                    getString(R.string.email_already_in_use_message) -> binding.emailTextInput.error = message

                    else -> Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}
