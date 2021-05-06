package com.example.userlist.di.components

import android.app.Application
import android.content.Context
import com.example.userlist.di.modules.*
import com.example.userlist.domain.UserInteractor
import com.example.userlist.presentation.details.UserDetailsFragment
import com.example.userlist.presentation.list.UserListFragment
import com.example.userlist.presentation.viewmodel.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class, DatabaseModule::class, RepoModule::class, InteractorModule::class])
interface AppComponent {

    fun context(): Context

    fun userInteractor(): UserInteractor

    fun provideViewModelFactory(): ViewModelFactory

    fun inject(fragment: UserDetailsFragment)

    fun inject(fragment: UserListFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}