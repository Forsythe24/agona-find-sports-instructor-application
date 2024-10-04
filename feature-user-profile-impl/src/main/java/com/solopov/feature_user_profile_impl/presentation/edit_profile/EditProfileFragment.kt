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
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentEditProfileBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.base.view.ProgressButton
import com.solopov.common.di.FeatureUtils
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile


class EditProfileFragment : BaseFragment<EditProfileViewModel>() {
    private lateinit var binding: FragmentEditProfileBinding

    private var currentUser: UserProfile? = null
    private lateinit var dialog: Dialog
    private lateinit var dialogButton: ProgressButton
    private lateinit var passwordEt: TextInputEditText
    private lateinit var passwordTextInput: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
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
                viewModel.goBack()
            }

            saveBtn.setOnClickListener {

                ageTextInput.error = viewModel.validateAge(ageEt.text.toString())
                nameTextInput.error = viewModel.validateName(nameEt.text.toString())

                if (isValidForm()) {
                    currentUser?.let {
                        it.name = nameEt.text.toString()
                        it.age = ageEt.text.toString().toInt()
                        it.gender = if (maleRb.isChecked) getString(R.string.male_gender_mark) else getString(R.string.female_gender_mark)
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
            return ageTextInput.error.isNullOrEmpty() && nameTextInput.error.isNullOrEmpty()
        }
    }

    private fun initUser() {
        extractUserArgument()?.let {
            viewModel.setCurrentUser(it)
        }
    }

    private fun onUserUpdatedCallback() {
        viewModel.goBack()
        Snackbar.make(
            binding.root,
            getString(R.string.your_data_has_been_successfully_saved),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun initDialogViews() {
        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_password_setting)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogButton = dialog.findViewById(R.id.take_user_data_btn)
        passwordEt = dialog.findViewById(R.id.password_et)
        passwordTextInput = dialog.findViewById(R.id.password_text_input)

        dialog.window?.setLayout(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.MATCH_PARENT
        )

        viewModel.passwordErrorTextFlow.observe { text ->
            passwordTextInput.error = text
        }
    }


    private fun showCurrentPasswordInputDialog() {
        dialog.show()
        dialogButton.setOnClickListener {
            currentUser?.let {
                viewModel.verifyCredentials(
                    passwordEt.text.toString(), ::onCorrectPassword, ::onWrongPassword
                )
            }
        }
    }

    private fun onCorrectPassword() {
        dialog.hide()
        showPasswordSettingDialog()
    }

    private fun onWrongPassword() {
        passwordTextInput.error = getString(R.string.wrong_password)
    }

    private fun showPasswordSettingDialog() {
        dialog.show()

        passwordEt.setText("")
        passwordTextInput.error = null

        val tvHeader = dialog.findViewById<TextView>(R.id.header_tv)
        tvHeader.text = getString(R.string.new_password_header)
        dialogButton.setText(getString(R.string.submit))

        dialogButton.setOnClickListener {
            val potentialNewPassword = passwordEt.text.toString()
            passwordTextInput.error = viewModel.validatePassword(potentialNewPassword)
            if (passwordTextInput.error.isNullOrEmpty()) {
                currentUser?.let {
                    viewModel.updateUserPassword(potentialNewPassword, ::onPasswordChangedCallback)
                }
            }
        }
    }

    private fun onPasswordChangedCallback() {
        dialog.hide()
        Snackbar.make(
            binding.root,
            getString(R.string.password_has_been_successfully_saved),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun extractUserArgument(): UserProfile? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ParamsKey.USER_KEY, UserProfile::class.java)
        } else {
            requireArguments().getSerializable(ParamsKey.USER_KEY) as UserProfile
        }
    }


    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(
            this, UserProfileFeatureApi::class.java
        ).editProfileComponentFactory().create(this).inject(this)
    }

    override fun subscribe(viewModel: EditProfileViewModel) {
        with(viewModel) {
            editProfileFlow.observe { userProfile ->
                currentUser = userProfile

                with(binding) {

                    userProfile?.let { user ->
                        nameEt.setText(user.name)
                        ageEt.setText(user.age.toString())
                        if (user.gender == getString(R.string.male_gender_mark)) {
                            maleRb.isChecked = true
                        } else {
                            femaleRb.isChecked = true
                        }

                        if (user.isInstructor.not()) {
                            instructorsBioBtn.visibility = GONE
                        }

                        instructorsBioBtn.setOnClickListener {
                            viewModel.goToInstructApplication(user)
                        }
                    }
                }
            }

            saveBtnProgressBarFlow.observe { isLoading ->
                binding.saveBtn.setLoading(isLoading)
            }

            dialogBtnProgressBarFlow.observe { isLoading ->
                binding.saveBtn.setLoading(isLoading)
            }

            errorMessageChannel.observe { message ->
                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
            }
        }

    }

}
