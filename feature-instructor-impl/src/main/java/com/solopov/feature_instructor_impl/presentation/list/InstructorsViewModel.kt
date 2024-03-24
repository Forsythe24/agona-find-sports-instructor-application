package com.solopov.feature_instructor_impl.presentation.list

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_api.domain.model.Instructor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InstructorsViewModel @Inject constructor (
    private val interactor: InstructorInteractor,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {
    private val _currentInstructorsFlow = MutableStateFlow<List<InstructorsAdapter.ListItem>?>(null)
    val currentInstructorsFlow: StateFlow<List<InstructorsAdapter.ListItem>?>
        get() = _currentInstructorsFlow

    val errorsChannel = Channel<Throwable>()

    fun getInstructorsBySportId(sportId: Int) {
        viewModelScope.launch {
            runCatching {
                interactor.getInstructorsBySportId(sportId)
            }.onSuccess {
                _currentInstructorsFlow.value = mapInstructorsToListItems(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    private fun mapInstructorToListItem(instructor: Instructor): InstructorsAdapter.ListItem {
        return with(instructor) {
            InstructorsAdapter.ListItem(id, name, age, gender, sport, photo, experience, description, rating, hourlyRate)
        }
    }

    private fun mapInstructorsToListItems(instructors: List<Instructor>): List<InstructorsAdapter.ListItem> {
        return instructors.map(::mapInstructorToListItem)
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }

}
