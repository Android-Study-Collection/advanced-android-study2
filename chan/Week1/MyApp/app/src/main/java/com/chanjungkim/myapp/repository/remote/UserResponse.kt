package com.chanjungkim.myapp.repository.remote

import com.chanjungkim.myapp.repository.data.User
import com.chanjungkim.myapp.repository.data.UserForm

data class UserResponse(
    val name: String,
    val age: Int,
    val gender: String,
    val success: Boolean
) {
    fun getUserForm() = UserForm(name, gender)

    fun getUser() = User(name, age, gender)
}