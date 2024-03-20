package com.solopov.feature_instructor_impl.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_instructor_impl.di.InstructorFeatureComponent
import com.solopov.feature_instructor_impl.utils.ParamKeys
import com.solopov.instructors.databinding.FragmentInstructorBinding
import javax.inject.Inject


class InstructorFragment : BaseFragment<InstructorViewModel>() {
    companion object {
        fun createBundle(instructorId: String): Bundle = bundleOf(ParamKeys.KEY_INSTRUCTOR_ID to instructorId)
    }

    @Inject
    lateinit var router: InstructorsRouter

    private lateinit var binding: FragmentInstructorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInstructorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun inject() {
        val instructorId = requireArguments().getString(ParamKeys.KEY_INSTRUCTOR_ID, "")
        FeatureUtils.getFeature<InstructorFeatureComponent>(this, InstructorFeatureApi::class.java)
            .instructorComponentFactory()
            .create(this, instructorId)
            .inject(this)
    }

    override fun initViews() {
    }


    override fun subscribe(viewModel: InstructorViewModel) {
        TODO("Not yet implemented")
    }
}
