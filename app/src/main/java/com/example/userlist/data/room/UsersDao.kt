package com.example.userlist.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.userlist.data.model.UserModel
import com.example.userlist.data.room.relations.UserCrossRef
import com.example.userlist.data.room.relations.UserWithFriends

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(list: List<UserModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRefs(list: List<UserCrossRef>)

    @Transaction
    suspend fun insertUsersAndFriends(users: List<UserModel>, refs: List<UserCrossRef>) {
        insertUsers(users)
        insertRefs(refs)
    }

    @Query("SELECT * FROM user")
    fun getUsers(): LiveData<List<UserModel>>

    @Transaction
    @Query("SELECT * FROM user where id = :userId")
    fun getUserWithFriends(userId: Int): LiveData<UserWithFriends>
}