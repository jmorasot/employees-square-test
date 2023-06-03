package com.juanmora.square.employeelist.features.home.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanmora.square.employeelist.features.core.domain.Response
import com.juanmora.square.employeelist.features.employees.domain.models.Employee
import com.juanmora.square.employeelist.features.home.domain.repositories.EmptyResultsError
import com.juanmora.square.employeelist.features.home.domain.repositories.InvalidResultsError
import com.juanmora.square.employeelist.features.home.domain.usecases.FetchEmployeeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val fetchEmployeeListUseCase: FetchEmployeeListUseCase
) : ViewModel() {

    private val _employeeUIStateSharedFlow = MutableSharedFlow<EmployeeListUIState>()
    val employeeUIStateSharedFlow = _employeeUIStateSharedFlow.asSharedFlow()

    fun loadEmployeeContent(forceUpdate: Boolean = false) {
        fetchEmployeeListUseCase(forceUpdate)
            .onStart { _employeeUIStateSharedFlow.emit(EmployeeListUIState.Loading(true)) }
            .onEach { handleEmployeesResult(it) }
            .onCompletion { _employeeUIStateSharedFlow.emit(EmployeeListUIState.Loading(false)) }
            .catch { handleLoadEmployeesError(it) }
            .launchIn(viewModelScope)
    }

    private fun handleEmployeesResult(response: Response<List<Employee>>) {
        if (response is Response.Success) {
            val list = response.data
            if (list.isEmpty()) {
                handleLoadEmployeesError(EmptyResultsError())
            }
            viewModelScope.launch {
                _employeeUIStateSharedFlow.emit(
                    EmployeeListUIState.Success(list)
                )
            }
        } else {
            handleLoadEmployeesError((response as Response.Error).exception)
        }
    }

    private fun handleLoadEmployeesError(error: Throwable) {
        error.printStackTrace()
        viewModelScope.launch {
            _employeeUIStateSharedFlow.emit(
                when (error) {
                    is EmptyResultsError -> EmployeeListUIState.Error(EmployeeListUIState.Error.Codes.EmptyResults)
                    is InvalidResultsError -> EmployeeListUIState.Error(EmployeeListUIState.Error.Codes.InvalidResults)
                    else -> EmployeeListUIState.Error(EmployeeListUIState.Error.Codes.Unknown)
                }
            )
        }
    }

    sealed class EmployeeListUIState {
        class Success(val elements: List<Employee>) : EmployeeListUIState()
        class Loading(val show: Boolean) : EmployeeListUIState()
        class Error(val errorCode: Codes) : EmployeeListUIState() {
            enum class Codes {
                EmptyResults,
                InvalidResults,
                Unknown
            }
        }
    }
}
