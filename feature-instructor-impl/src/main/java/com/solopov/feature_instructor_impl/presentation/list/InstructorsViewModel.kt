package com.solopov.feature_instructor_impl.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_impl.di.InstructorFeatureComponent
import com.solopov.instructors.databinding.FragmentInstructorBinding
import com.solopov.instructors.databinding.FragmentInstructorsBinding
import javax.inject.Inject

class InstructorsViewModel (
    private val interactor: InstructorInteractor,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

}
