package com.solopov.feature_authentication_impl.presentation.signup

import android.media.MediaRouter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import com.solopov.feature_authentication_impl.databinding.FragmentSignUpBinding
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent
import com.solopov.feature_authentication_impl.presentation.SignUpViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.exp

class SignUpFragment: BaseFragment<SignUpViewModel>() {

    private lateinit var binding: FragmentSignUpBinding

    @Inject
    lateinit var router: AuthRouter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {

        with (viewModel) {
            with(binding) {
                finishSignUpBtn.setOnClickListener {
                    createUser(
                        email = emailEt.text.toString(),
                        password = passwordEt.text.toString(),
                        name = nameEt.text.toString(),
                        age = ageEt.text.toString().toInt(),
                        gender = "M",
                    )
                }

                lifecycleScope.launch {
                    errorsChannel.consumeEach { error ->
                        println(error)
                        val errorMessage = error.message ?: getString(R.string.unknown_error)
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<AuthFeatureComponent>(this, AuthFeatureApi::class.java)
            .signUpComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: SignUpViewModel) {
        viewModel.currentInstructorsFlow.observe {

        }
    }

}
