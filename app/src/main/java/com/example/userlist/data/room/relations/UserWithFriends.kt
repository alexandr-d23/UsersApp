package com.example.userlist.data.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.userlist.data.model.UserModel

data class UserWithFriends(
    @Embedded
    val user: UserModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            UserCrossRef::class,
            parentColumn = "userId",
            entityColumn = "friendId"
        )
    )
    val friends: List<UserModel>
)