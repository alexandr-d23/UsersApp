package com.example.userlist.data.retrofit

import com.example.userlist.data.model.UserResponse
import retrofit2.http.GET

interface UsersApi {
    @GET("users.json?alt=media")
    suspend fun getUsers(): List<UserResponse>
}