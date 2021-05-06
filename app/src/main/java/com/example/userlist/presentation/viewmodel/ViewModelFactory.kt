package com.example.userlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userlist.domain.UserInteractor
import com.example.userlist.presentation.details.UserDetailsViewModel
import com.example.userlist.presentation.list.UserListViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class ViewModelFactory @Inject constructor(
    private val userRepository: UserInteractor,
    @Named("IO") private val coroutineContext: CoroutineContext
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(UserListViewModel::class.java) -> {
                UserListViewModel(userRepository, coroutineContext) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            modelClass.isAssignableFrom(UserDetailsViewModel::class.java) -> {
                UserDetailsViewModel(userRepository) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            else -> {
                throw IllegalArgumentException("Unknown viewmodel class")
            }
        }


}