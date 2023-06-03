package com.juanmora.square.employeelist.features.employees.domain.models

data class Employee(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val bio: String,
    val smallAvatarUrl: String,
    val largeAvatarUrl: String,
    val teamName: String,
    val type: EmployeeType
)
