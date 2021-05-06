package com.example.userlist.di.modules

import android.content.Context
import androidx.room.Room
import com.example.userlist.data.room.UsersDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): UsersDB =
        Room.databaseBuilder(
            context,
            UsersDB::class.java,
            "db"
        ).build()


    @Provides
    @Singleton
    fun provideDao(instance: UsersDB) = instance.usersDao

}