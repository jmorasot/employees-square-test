package com.juanmora.square.employeelist.features.home.data.mappers

import com.juanmora.square.employeelist.features.employees.domain.models.Employee
import com.juanmora.square.employeelist.features.employees.domain.models.EmployeeType
import com.juanmora.square.employeelist.features.home.data.api.responses.EmployeeItemResponse

class EmployeeResponseMapper {

    fun map(from: EmployeeItemResponse): Employee =
        from.run {
            Employee(
                id = id,
                name = name,
                email = email,
                phone = phone,
                bio = bio,
                smallAvatarUrl = smallAvatarUrl,
                largeAvatarUrl = largeAvatarUrl,
                teamName = teamName,
                type = EmployeeType.valueOf(type)
            )
        }

    fun mapList(from: List<EmployeeItemResponse>): List<Employee> =
        from.map { map(it) }
}
