package com.example.userlist.di.modules

import com.example.userlist.domain.UserInteractor
import com.example.userlist.domain.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Singleton
    @Provides
    fun provideUserInteractor(userRepository: UserRepository): UserInteractor =
        UserInteractor(userRepository)
}