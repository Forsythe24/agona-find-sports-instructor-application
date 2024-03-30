package com.solopov.feature_authentication_impl.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository
import com.solopov.feature_authentication_impl.databinding.FragmentSignUpBinding
import com.solopov.feature_authentication_impl.di.AuthFeatureComponent
import com.solopov.feature_authentication_impl.presentation.SignUpViewModel
import javax.inject.Inject

class SignUpFragment: BaseFragment<SignUpViewModel>() {

    private lateinit var binding: FragmentSignUpBinding

    @Inject
    lateinit var repository: AuthRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun initViews() {
        with(binding) {
            finishSignUpBtn.setOnClickListener {
                repository.createUser(emailEt.text.toString(), passwordEt.text.toString())
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
