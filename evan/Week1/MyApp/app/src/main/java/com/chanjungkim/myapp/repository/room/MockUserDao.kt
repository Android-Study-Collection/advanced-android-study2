package com.chanjungkim.myapp.repository.room

import com.chanjungkim.myapp.repository.data.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class MockUserDao {
    suspend fun addUser(user: User) = coroutineScope {
        delay(1000)
        // add user
    }
}