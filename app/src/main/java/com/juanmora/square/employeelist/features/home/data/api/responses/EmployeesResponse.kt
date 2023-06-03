package com.juanmora.square.employeelist.features.home.data.api.responses

import com.google.gson.annotations.SerializedName

data class EmployeesResponse(
    @SerializedName("employees")
    val employees: List<EmployeeItemResponse>
)
