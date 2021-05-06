package com.example.userlist.domain

import androidx.lifecycle.LiveData
import com.example.userlist.domain.model.User

class UserInteractor(
    private val userRepository: UserRepository
) {
    fun getUsers(): LiveData<List<User>?> = userRepository.getUsers()

    fun getUserWithFriendsById(userId: Int): LiveData<User?> =
        userRepository.getUserWithFriendsById(userId)

    suspend fun refreshUsers() = userRepository.refreshUsers()
}