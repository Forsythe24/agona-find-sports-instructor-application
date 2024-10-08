package com.solopov.feature_instructor_impl.presentation

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.feature_instructor_api.domain.InstructorInteractor
import com.solopov.feature_instructor_api.domain.model.Instructor
import com.solopov.feature_instructor_impl.InstructorsRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructorsViewModel @Inject constructor(
    private val interactor: InstructorInteractor,
    private val router: InstructorsRouter,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {
    private val _currentInstructorsFlow = MutableStateFlow<List<InstructorsAdapter.ListItem>?>(null)
    val currentInstructorsFlow: StateFlow<List<InstructorsAdapter.ListItem>?>
        get() = _currentInstructorsFlow

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    fun getInstructorsBySportId(sportId: Int) {
        viewModelScope.launch {
            runCatching {
                interactor.getInstructorsBySportId(sportId)
            }.onSuccess {
                _currentInstructorsFlow.value = mapInstructorsToListItems(it)
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
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

    override fun onCleared() {
        _errorMessageChannel.close()
        super.onCleared()
    }

}
