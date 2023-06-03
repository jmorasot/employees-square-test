package com.juanmora.square.employeelist.features.utils

import com.juanmora.square.employeelist.features.employees.domain.models.Employee
import com.juanmora.square.employeelist.features.employees.domain.models.EmployeeType

object TestResources {

    fun getEmployeeList() = listOf(
        Employee(
            id = "0d8fcc12-4d0c-425c-8355-390b312b909c",
            name = "Justine Mason",
            phone = "5553280123",
            email = "jmason.demo@squareup.com",
            bio = "Engineer on the Point of Sale team.",
            smallAvatarUrl = "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
            largeAvatarUrl = "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg",
            teamName = "Point of Sale",
            type = EmployeeType.FULL_TIME
        ),
        Employee(
            id = "a98f8a2e-c975-4ba3-8b35-01f719e7de2d",
            name = "Camille Rogers",
            phone = "5558531970",
            email = "crogers.demo@squareup.com",
            bio = "Designer on the web marketing team.",
            smallAvatarUrl = "https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/small.jpg",
            largeAvatarUrl = "https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/large.jpg",
            teamName = "Public Web & Marketing",
            type = EmployeeType.PART_TIME
        ),
        Employee(
            id = "b8cf3382-ecf2-4240-b8ab-007688426e8c",
            name = "Richard Stein",
            phone = "5557223332",
            email = "rstein.demo@squareup.com",
            bio = "Product manager for the Point of sale app!",
            smallAvatarUrl = "https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/small.jpg",
            largeAvatarUrl = "https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/large.jpg",
            teamName = "Point of Sale",
            type = EmployeeType.PART_TIME
        )
    )

    fun getEmptyEmployeeList() = listOf<Employee>()
}
