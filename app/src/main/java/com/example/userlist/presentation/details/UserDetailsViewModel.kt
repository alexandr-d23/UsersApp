package com.example.userlist.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.userlist.domain.UserInteractor
import com.example.userlist.domain.model.User

class UserDetailsViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {

    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private var userLiveData: LiveData<User?>? = null
    private var navigationId: MutableLiveData<Int> = MutableLiveData()

    fun getUserById(userId: Int): LiveData<User?> =
        userLiveData ?: userInteractor.getUserWithFriendsById(userId).map {
            it
        }.also {
            userLiveData = it
        }

    fun itemClicked(id: Int, isActive: Boolean) {
        if (isActive) {
            navigationId.postValue(id)
        }
    }

    fun getError(): LiveData<Throwable> = errorLiveData

    fun navigationId(): LiveData<Int> = MutableLiveData<Int>().also {
        navigationId = it
    }

}