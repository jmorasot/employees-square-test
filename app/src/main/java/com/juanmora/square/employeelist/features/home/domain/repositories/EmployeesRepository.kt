package com.juanmora.square.employeelist.features.home.domain.repositories

import com.juanmora.square.employeelist.features.employees.domain.models.Employee

class EmptyResultsError : Throwable()
class InvalidResultsError : Throwable()

interface EmployeesRepository {
    suspend fun fetchEmployeeList(): List<Employee>
}
