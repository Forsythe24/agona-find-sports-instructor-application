package com.solopov.feature_authentication_impl.presentation.password_recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent
import javax.inject.Inject

class PasswordRecoveryFragment: BaseFragment<PasswordRecoveryViewModel>() {

    @Inject
    lateinit var router: AuthRouter

    @Inject
    lateinit var userDataValidator: UserDataValidator

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorBackground))
        composeView.setContent {
            val state by viewModel.state.collectAsState()
            PasswordRecoveryScreen(
                state = state,
                onSendClick = ::sendNewPasswordOnEmail,
                onBackClick = {router.goBack()},
                userDataValidator = userDataValidator
            )
        }
    }

    private fun sendNewPasswordOnEmail(email: String) {
        viewModel.sendNewPassword(email)
    }

    override fun initViews() {

    }


    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .passwordRecoveryComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: PasswordRecoveryViewModel) {

        viewModel.state.observe {  }
    }

}
