package com.example.userlist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.userlist.data.model.UserModel
import com.example.userlist.data.room.relations.UserCrossRef

@Database(
    entities = [
        UserModel::class,
        UserCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UsersDB : RoomDatabase() {
    abstract val usersDao: UsersDao
}