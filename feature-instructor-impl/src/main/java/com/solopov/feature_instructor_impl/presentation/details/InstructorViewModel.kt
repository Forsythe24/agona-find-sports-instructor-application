package com.solopov.feature_instructor_impl.presentation.details

import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_impl.presentation.details.model.InstructorDetailsModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class InstructorViewModel @Inject constructor(
    private val interactor: InstructorInteractor,
    private val isntructorId: String,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    private val _instructorFlow = MutableStateFlow<InstructorDetailsModel?>(null);



}
