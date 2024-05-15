package com.solopov.feature_user_profile_impl.presentation.edit_profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentEditProfileBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.utils.ParamsKey
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import javax.inject.Inject


class EditProfileFragment: BaseFragment<EditProfileViewModel>(){
    private lateinit var binding: FragmentEditProfileBinding

    @Inject
    lateinit var router: UserProfileRouter

    @Inject
    lateinit var userDataValidator: UserDataValidator

    private var currentUser: UserProfile? = null
    private lateinit var dialog: Dialog

    private lateinit var dialogButton: MaterialButton
    private lateinit var passwordEt: TextInputEditText
    private lateinit var passwordTextInput: TextInputLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUser()
    }
    override fun initViews() {
        with(binding) {

            backBtn.setOnClickListener {
                router.goBack()
            }

            saveBtn.setOnClickListener {

                ageTextInput.helperText = userDataValidator.validateAge(ageEt.text.toString())
                nameTextInput.helperText = userDataValidator.validateName(nameEt.text.toString())

                if (isValidForm()) {
                    currentUser?.let {
                        it.name = nameEt.text.toString()
                        it.age = ageEt.text.toString().toInt()
                        it.gender = if (maleRb.isChecked) "M" else "F"
                        viewModel.updateUser(it, ::onUserUpdatedCallback)
                        viewModel.setCurrentUser(it)
                    }
                }

            }

            changePasswordBtn.setOnClickListener {
                initDialogViews()
                showCurrentPasswordInputDialog()
            }

        }
    }

    private fun isValidForm(): Boolean {
        with(binding) {
            return ageTextInput.helperText.isNullOrEmpty() && nameTextInput.helperText.isNullOrEmpty()
        }
    }

    private fun initUser() {
        extractUserArgument()?.let {
            viewModel.setCurrentUser(it)
        }
    }

    private fun onUserUpdatedCallback() {
        router.goBack()
        Snackbar.make(binding.root, getString(R.string.your_data_has_been_successfully_saved), Snackbar.LENGTH_SHORT).show()
    }

    private fun initDialogViews() {
        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.password_setting_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogButton = dialog.findViewById(R.id.take_user_data_btn)
        passwordEt = dialog.findViewById(R.id.password_et)
        passwordTextInput = dialog.findViewById(R.id.password_text_input)
    }


    private fun showCurrentPasswordInputDialog() {
        dialog.show()

        dialogButton.setOnClickListener {
            currentUser?.let {
                viewModel.verifyCredentials(passwordEt.text.toString(), ::onCorrectPassword, ::onWrongPassword)
            }
        }
    }

    private fun onCorrectPassword() {
        dialog.hide()
        showPasswordSettingDialog()
    }

    private fun onWrongPassword() {
        passwordTextInput.helperText = getString(R.string.wrong_password)
    }

    private fun showPasswordSettingDialog() {
        dialog.show()

        passwordEt.setText("")
        passwordTextInput.helperText = ""

        val tvHeader = dialog.findViewById<TextView>(R.id.header_tv)
        tvHeader.text = getString(R.string.new_password_header)
        dialogButton.text = getString(R.string.submit)

        dialogButton.setOnClickListener {
            val potentialNewPassword = passwordEt.text.toString()
            passwordTextInput.helperText = userDataValidator.validatePassword(potentialNewPassword)
            if (passwordTextInput.helperText.isNullOrEmpty()) {
                currentUser?.let {
                    viewModel.updateUserPassword(potentialNewPassword, ::onPasswordChangedCallback)
                }
            }
        }
    }

    private fun onPasswordChangedCallback() {
        dialog.hide()
        Snackbar.make(binding.root, getString(R.string.password_has_been_successfully_saved), Snackbar.LENGTH_SHORT).show()
    }

    private fun extractUserArgument(): UserProfile? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ParamsKey.USER, UserProfile::class.java)
        } else {
            requireArguments().getSerializable(ParamsKey.USER) as UserProfile
        }
    }



    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(this, UserProfileFeatureApi::class.java)
            .editProfileComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: EditProfileViewModel) {
        with(viewModel) {
            editProfileFlow.observe { userProfile ->
                currentUser = userProfile

                with(binding) {

                    userProfile?.let { user ->
                        nameEt.setText(user.name)
                        ageEt.setText(user.age.toString())
                        if (user.gender == "M") {
                            maleRb.isChecked = true
                        } else {
                            femaleRb.isChecked = true
                        }

                        if (user.isInstructor.not()) {
                            instructorsBioBtn.visibility = GONE
                        }

                        instructorsBioBtn.setOnClickListener {
                            router.goToInstructApplication(user)
                        }
                    }
                }
            }

            progressBarFlow.observe { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }

    }

}
