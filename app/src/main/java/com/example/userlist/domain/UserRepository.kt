package com.example.userlist.domain

import androidx.lifecycle.LiveData
import com.example.userlist.domain.model.User

interface UserRepository {
    fun getUsers(): LiveData<List<User>?>
    fun getUserWithFriendsById(userId: Int): LiveData<User?>
    suspend fun refreshUsers()
}