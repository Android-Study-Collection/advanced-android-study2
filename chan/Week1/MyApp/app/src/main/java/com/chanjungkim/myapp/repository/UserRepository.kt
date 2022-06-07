package com.chanjungkim.myapp.repository

import com.chanjungkim.myapp.repository.data.UserForm
import com.chanjungkim.myapp.repository.remote.MockUserApi
import com.chanjungkim.myapp.repository.room.MockUserDao

/**
 * API 서버, DB control
 *
 * request param 생성
 *
 */
class UserRepository(
    private val userDao: MockUserDao,
    private val userApi: MockUserApi
) {
    suspend fun getUser(userId: String): UserForm {
        val userResponse = userApi.userResponse(userId)
        userDao.addUser(userResponse.getUser())
        return userResponse.getUserForm()
    }
}