package com.juanmora.square.employeelist.features.home.data.repositories

import com.juanmora.square.employeelist.features.BaseTest
import com.juanmora.square.employeelist.features.JsonHelper
import com.juanmora.square.employeelist.features.home.data.api.EmployeesApi
import com.juanmora.square.employeelist.features.home.data.mappers.EmployeeResponseMapper
import com.juanmora.square.employeelist.features.home.domain.repositories.EmployeesRepository
import com.juanmora.square.employeelist.features.utils.TestResources
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class RemoteEmployeesRepositoryTest : BaseTest() {

    private val mockWebServer = MockWebServer()

    private lateinit var repository: EmployeesRepository

    @Before
    fun setup() {
        super.beforeTest()
        mockWebServer.start()
        val url = mockWebServer.url("/").toString()
        val employeesApi = Retrofit.Builder()
            .baseUrl(url.substring(0, url.length - 1))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeesApi::class.java)

        repository = RemoteEmployeesRepository(
            employeesApi,
            EmployeeResponseMapper()
        )
    }

    @Test
    fun `get all posts from server with success`() = runBlocking {
        val employee = TestResources.getEmployeeList().first()
        val employeeName = employee.name
        val employeeBio = employee.bio
        val employeeType = employee.type
        val employeeTeam = employee.teamName
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(JsonHelper.getJsonFromFile("json/fetch_employees.json"))

        mockWebServer.enqueue(response)

        val employees = repository.fetchEmployeeList()
        val firstResult = employees.first()
        val request = mockWebServer.takeRequest()

        Assert.assertEquals(request.method, "GET")
        Assert.assertEquals(request.path, "/employees.json")

        Assert.assertTrue(employees.isNotEmpty())
        Assert.assertEquals(employees.size, 3)
        Assert.assertEquals(firstResult.name, employeeName)
        Assert.assertEquals(firstResult.bio, employeeBio)
        Assert.assertEquals(firstResult.type, employeeType)
        Assert.assertEquals(firstResult.teamName, employeeTeam)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}
