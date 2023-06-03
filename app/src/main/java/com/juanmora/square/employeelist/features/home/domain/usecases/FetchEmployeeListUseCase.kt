package com.juanmora.square.employeelist.features.home.domain.usecases

import com.juanmora.square.employeelist.features.core.domain.Response
import com.juanmora.square.employeelist.features.employees.domain.models.Employee
import com.juanmora.square.employeelist.features.home.domain.repositories.EmployeesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchEmployeeListUseCase @Inject constructor(
    private val employeesRepository: EmployeesRepository
) {

    private val employeeListCache = MutableStateFlow<List<Employee>>(listOf())

    operator fun invoke(forceUpdate: Boolean): Flow<Response<List<Employee>>> = flow {
        if (!forceUpdate && employeeListCache.value.isNotEmpty()) {
            emit(Response.Success(employeeListCache.value))
        } else {
            val list = employeesRepository.fetchEmployeeList()
            employeeListCache.value = list
            emit(Response.Success(list))
        }
    }
}
