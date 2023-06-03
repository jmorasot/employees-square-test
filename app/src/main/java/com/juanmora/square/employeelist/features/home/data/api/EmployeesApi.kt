package com.juanmora.square.employeelist.features.home.data.api

import com.juanmora.square.employeelist.features.home.data.api.responses.EmployeesResponse
import retrofit2.http.GET

interface EmployeesApi {
    @GET("employees.json")
    suspend fun fetchEmployeesList(): EmployeesResponse

    @GET("employees_empty.json")
    suspend fun fetchEmptyEmployeesList(): EmployeesResponse

    @GET("employees_malformed.json")
    suspend fun fetchMalformedEmployeesList(): EmployeesResponse
}
