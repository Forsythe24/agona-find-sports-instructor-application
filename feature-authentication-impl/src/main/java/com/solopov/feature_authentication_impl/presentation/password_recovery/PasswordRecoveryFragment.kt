package com.solopov.feature_authentication_impl.presentation.password_recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.R
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent

class PasswordRecoveryFragment : BaseFragment<PasswordRecoveryViewModel>() {

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorBackground
            )
        )
        composeView.setContent {
            PasswordRecoveryScreen(
                onSendClicked = ::sendNewPasswordOnEmail,
                onBackClicked = { viewModel.goBack() },
                onValidateEmail = viewModel::validateEmail
            )
        }
    }

    private fun sendNewPasswordOnEmail(email: String) {
        viewModel.sendNewPassword(email, ::onPasswordSent)
    }

    override fun initViews() {}


    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .passwordRecoveryComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: PasswordRecoveryViewModel) {
        viewModel.message.observe { message ->
            showSnackbar(message.text, Snackbar.LENGTH_SHORT)
        }
    }

    private fun onPasswordSent(email: String) {
        Toast.makeText(requireContext(), getString(R.string.new_password_sent_template).format(email), Toast.LENGTH_LONG).show()
    }

}
