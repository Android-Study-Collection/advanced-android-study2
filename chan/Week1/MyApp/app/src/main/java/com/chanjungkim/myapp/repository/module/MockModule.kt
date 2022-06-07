package com.chanjungkim.myapp.repository.module

import com.chanjungkim.myapp.repository.UserRepository
import com.chanjungkim.myapp.repository.remote.MockUserApi
import com.chanjungkim.myapp.repository.room.MockUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MockModule {
    @Singleton
    @Provides
    fun provideRepository(dao: MockUserDao, api: MockUserApi) = UserRepository(dao, api)

    @Singleton
    @Provides
    fun provideMockDAO() = MockUserDao()

    @Singleton
    @Provides
    fun provideMockAPI() = MockUserApi()
}