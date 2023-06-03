package com.juanmora.square.employeelist.features.home.view.viewmodels

import app.cash.turbine.test
import com.juanmora.square.employeelist.features.BaseTest
import com.juanmora.square.employeelist.features.core.domain.Response
import com.juanmora.square.employeelist.features.home.domain.repositories.InvalidResultsError
import com.juanmora.square.employeelist.features.home.domain.usecases.FetchEmployeeListUseCase
import com.juanmora.square.employeelist.features.home.view.viewmodels.EmployeeListViewModel.EmployeeListUIState.Error
import com.juanmora.square.employeelist.features.home.view.viewmodels.EmployeeListViewModel.EmployeeListUIState.Loading
import com.juanmora.square.employeelist.features.home.view.viewmodels.EmployeeListViewModel.EmployeeListUIState.Success
import com.juanmora.square.employeelist.features.utils.TestResources
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EmployeeListViewModelTest : BaseTest() {

    @MockK(relaxed = true)
    private lateinit var useCase: FetchEmployeeListUseCase

    private lateinit var viewModel: EmployeeListViewModel

    @Before
    fun setup() {
        super.beforeTest()
        viewModel = EmployeeListViewModel(
            fetchEmployeeListUseCase = useCase
        )
    }

    @Test
    fun `fetch employee list with success and valid data`() = runTest {
        coEvery { useCase(true) } returns flow {
            emit(Response.Success(TestResources.getEmployeeList()))
        }

        val job = launch {
            viewModel.employeeUIStateSharedFlow.test {
                assert(awaitItem() is Loading)
                val item = awaitItem()
                assert(item is Success)
                assert((item as Success).elements == TestResources.getEmployeeList())
                assert(awaitItem() is Loading)
            }

            viewModel.loadEmployeeContent(true)
        }

        job.run {
            join()
            cancel()
        }
    }

    @Test
    fun `fetch employee list with success and empty data`() = runTest {
        coEvery { useCase(true) } returns flow {
            emit(Response.Success(TestResources.getEmployeeList()))
        }

        val job = launch {
            viewModel.employeeUIStateSharedFlow.test {
                assert(awaitItem() is Loading)
                val item = awaitItem()
                assert(item is Error)
                assert((item as Error).errorCode == Error.Codes.EmptyResults)
                assert(awaitItem() is Loading)
            }

            viewModel.loadEmployeeContent(true)
        }

        job.run {
            join()
            cancel()
        }
    }

    @Test
    fun `fetch employee list with error`() = runTest {
        coEvery { useCase(true) } returns flow {
            emit(Response.Error(InvalidResultsError()))
        }

        val job = launch {
            viewModel.employeeUIStateSharedFlow.test {
                assert(awaitItem() is Loading)
                val item = awaitItem()
                assert(item is Error)
                assert((item as Error).errorCode == Error.Codes.InvalidResults)
                assert(awaitItem() is Loading)
            }

            viewModel.loadEmployeeContent(true)
        }

        job.run {
            join()
            cancel()
        }
    }

    @Test
    fun `fetch employee list with unknown error`() = runTest {
        val error = Throwable("Unknown error")
        coEvery { useCase(true) } throws error

        val job = launch {
            viewModel.employeeUIStateSharedFlow.test {
                assert(awaitItem() is Loading)
                val item = awaitItem()
                assert(item is Error)
                assert((item as Error).errorCode == Error.Codes.Unknown)
                assert(awaitItem() is Loading)
            }

            viewModel.loadEmployeeContent(true)
        }

        job.run {
            join()
            cancel()
        }
    }
}
