package com.solopov.feature_authentication_impl.presentation.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.solopov.common.base.BaseFragment
import com.solopov.common.data.remote.exceptions.AuthException
import com.solopov.common.di.FeatureUtils
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.R
import com.solopov.feature_authentication_impl.databinding.FragmentSignUpBinding
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class SignUpFragment : BaseFragment<SignUpViewModel>() {

    private lateinit var binding: FragmentSignUpBinding

    @Inject
    lateinit var validator: UserDataValidator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

            emailEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    emailTextInput.helperText = validator.validateEmail(text.toString())
                }
                override fun afterTextChanged(p0: Editable?) {}
            })

            passwordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    passwordTextInput.helperText = validator.validatePassword(text.toString())
                }
                override fun afterTextChanged(p0: Editable?) {}
            })

            nameEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    nameTextInput.helperText = validator.validateName(text.toString())
                }
                override fun afterTextChanged(p0: Editable?) {}
            })

            ageEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    ageTextInput.helperText = validator.validateAge(ageEt.text.toString())
                }
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun showInvalidFormAlert() {
        var message = ""
        with(binding) {
            if (emailTextInput.helperText != null)
                message += getString(R.string.alert_email) + emailTextInput.helperText

            if (passwordTextInput.helperText != null)
                message += getString(R.string.alert_password) + passwordTextInput.helperText

            if (nameTextInput.helperText != null)
                message += getString(R.string.alert_name) + nameTextInput.helperText

            if (ageTextInput.helperText != null)
                message += getString(R.string.alert_age) + ageTextInput.helperText
        }

        showAlert(getString(R.string.invalid_form), message)
    }

    private fun isValidForm(): Boolean {
        with(binding) {
            emailTextInput.helperText = validator.validateEmail(emailEt.text.toString())
            passwordTextInput.helperText = validator.validatePassword(passwordEt.text.toString())
            nameTextInput.helperText = validator.validateName(nameEt.text.toString())
            ageTextInput.helperText = validator.validateAge(ageEt.text.toString())

            return emailTextInput.helperText == null && passwordTextInput.helperText == null && nameTextInput.helperText == null && ageTextInput.helperText == null
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

            errorsChannel.receiveAsFlow().observe { error ->
                val errorMessage = error.message ?: getString(R.string.unknown_error)

                when (error) {

                    is AuthException.EmailAlreadyInUseException -> binding.emailTextInput.helperText =
                        errorMessage

                    else -> Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}
