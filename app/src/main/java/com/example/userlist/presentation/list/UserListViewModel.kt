package com.example.userlist.presentation.list

import androidx.lifecycle.*
import com.example.userlist.domain.UserInteractor
import com.example.userlist.presentation.model.UserItem
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserListViewModel(
    private val userRepository: UserInteractor,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    private var userLiveData: LiveData<List<UserItem>?>? = null
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private var navigationId: MutableLiveData<Int> = MutableLiveData()
    private var isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getUsers(): LiveData<List<UserItem>?> = userLiveData ?: userRepository.getUsers().map {
        it?.map { user ->
            UserItem(
                user.id,
                user.email,
                user.name,
                user.isActive
            )
        }
    }.also {
        userLiveData = it
    }

    fun itemClicked(id: Int, isActive: Boolean) {
        if (isActive) {
            navigationId.postValue(id)
        }
    }

    fun navigationId(): LiveData<Int> = MutableLiveData<Int>().also {
        navigationId = it
    }

    fun isLoading(): LiveData<Boolean> = isLoadingLiveData

    fun getError(): LiveData<Throwable> = errorLiveData

    fun refreshUser() {
        viewModelScope.launch(coroutineContext) {
            runCatching {
                isLoadingLiveData.postValue(true)
                userRepository.refreshUsers()
            }.onSuccess {
                isLoadingLiveData.postValue(false)
            }.onFailure {
                errorLiveData.postValue(it)
                isLoadingLiveData.postValue(false)
            }
        }
    }
}