package com.solopov.feature_instructor_impl.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_instructor_impl.di.InstructorFeatureComponent
import com.solopov.instructors.databinding.FragmentInstructorsBinding
import javax.inject.Inject


class InstructorsFragment : BaseFragment<InstructorsViewModel>() {
    @Inject
    lateinit var router: InstructorsRouter

    private lateinit var binding: FragmentInstructorsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInstructorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun inject() {
        FeatureUtils.getFeature<InstructorFeatureComponent>(this, InstructorFeatureApi::class.java)
            .instructorsComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: InstructorsViewModel) {
        TODO("Not yet implemented")
    }

    override fun initViews() {
        with(binding) {
        }
    }

}
