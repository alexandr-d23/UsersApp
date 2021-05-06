package com.example.userlist.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.userlist.data.retrofit.UsersApi
import com.example.userlist.data.room.UsersDao
import com.example.userlist.data.room.relations.UserCrossRef
import com.example.userlist.domain.UserRepository
import com.example.userlist.domain.model.User
import com.example.userlist.util.userModelToUser
import com.example.userlist.util.userModelWithFriendsToUser
import com.example.userlist.util.userResponseToUserModel

class UserRepositoryImpl(
    private val usersApi: UsersApi,
    private val usersDao: UsersDao
) : UserRepository {

    override fun getUsers(): LiveData<List<User>?> = usersDao.getUsers().map { list ->
        list.map(::userModelToUser)
    }

    override fun getUserWithFriendsById(userId: Int): LiveData<User?> =
        usersDao.getUserWithFriends(userId).map { userWithFriends ->
            userModelWithFriendsToUser(userWithFriends)
        }

    override suspend fun refreshUsers() {
        val refs = mutableListOf<UserCrossRef>()
        val users = usersApi.getUsers().onEach { userResponse ->
            userResponse.friends.onEach { friend ->
                refs.add(UserCrossRef(userResponse.id, friend.id))
            }
        }.map(::userResponseToUserModel)
        usersDao.insertUsersAndFriends(users, refs)
    }

}