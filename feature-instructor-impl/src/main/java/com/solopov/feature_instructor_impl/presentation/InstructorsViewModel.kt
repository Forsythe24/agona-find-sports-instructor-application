package com.solopov.feature_instructor_impl.presentation

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_api.domain.usecase.LoadSportInstructorsUseCase
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_instructor_impl.presentation.recycler_view.InstructorsAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructorsViewModel @Inject constructor(
    private val router: InstructorsRouter,
    private val resourceManager: ResourceManager,
    private val loadSportInstructorsUseCase: LoadSportInstructorsUseCase
) : BaseViewModel() {
    private val _currentInstructorsState = MutableStateFlow<List<InstructorsAdapter.ListItem>?>(null)
    val currentInstructorsState: StateFlow<List<InstructorsAdapter.ListItem>?> = _currentInstructorsState.asStateFlow()

    fun getInstructorsBySportId(sportId: Int) {
        viewModelScope.launch {
            runCatching {
                loadSportInstructorsUseCase(sportId)
            }.onSuccess {
                _currentInstructorsState.value = mapInstructorsToListItems(it)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    private fun mapInstructorToListItem(instructor: Instructor): InstructorsAdapter.ListItem {
        return with(instructor) {
            InstructorsAdapter.ListItem(
                id,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                numberOfRatings,
                hourlyRate,
            )
        }
    }

    private fun mapInstructorsToListItems(instructors: List<Instructor>): List<InstructorsAdapter.ListItem> {
        return instructors.map(::mapInstructorToListItem)
    }

    fun openInstructor(instructorId: String) {
        router.openInstructor(instructorId)
    }
}
