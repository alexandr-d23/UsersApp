package com.example.userlist.di.modules

import com.example.userlist.data.repositories.UserRepositoryImpl
import com.example.userlist.data.retrofit.UsersApi
import com.example.userlist.data.room.UsersDao
import com.example.userlist.domain.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        usersApi: UsersApi,
        usersDao: UsersDao
    ): UserRepository = UserRepositoryImpl(usersApi, usersDao)

}