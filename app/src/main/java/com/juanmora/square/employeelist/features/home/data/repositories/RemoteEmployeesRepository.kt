package com.juanmora.square.employeelist.features.home.data.repositories

import com.juanmora.square.employeelist.features.employees.domain.models.Employee
import com.juanmora.square.employeelist.features.home.data.api.EmployeesApi
import com.juanmora.square.employeelist.features.home.data.mappers.EmployeeResponseMapper
import com.juanmora.square.employeelist.features.home.domain.repositories.EmployeesRepository
import javax.inject.Inject

class RemoteEmployeesRepository @Inject constructor(
    private val employeesApi: EmployeesApi,
    private val employeeMapper: EmployeeResponseMapper
) : EmployeesRepository {

    override suspend fun fetchEmployeeList(): List<Employee> {
        val employees = employeesApi.fetchEmployeesList()
        return employeeMapper.mapList(employees.employees)
    }
}
