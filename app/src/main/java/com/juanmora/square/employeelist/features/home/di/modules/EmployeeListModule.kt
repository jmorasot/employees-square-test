package com.juanmora.square.employeelist.features.home.di.modules

import com.juanmora.square.employeelist.features.home.data.api.EmployeesApi
import com.juanmora.square.employeelist.features.home.data.mappers.EmployeeResponseMapper
import com.juanmora.square.employeelist.features.home.data.repositories.RemoteEmployeesRepository
import com.juanmora.square.employeelist.features.home.domain.repositories.EmployeesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmployeeListModule {

    @Singleton
    @Provides
    fun provideEmployeesRepository(employeesApi: EmployeesApi): EmployeesRepository {
        return RemoteEmployeesRepository(
            employeesApi = employeesApi,
            employeeMapper = EmployeeResponseMapper()
        )
    }

    @Singleton
    @Provides
    fun providePostsApi(retrofit: Retrofit): EmployeesApi = retrofit.create(EmployeesApi::class.java)
}
