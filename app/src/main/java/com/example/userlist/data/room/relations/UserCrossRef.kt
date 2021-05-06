package com.example.userlist.data.room.relations

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "friendId"])
data class UserCrossRef(
    val userId: Int,
    val friendId: Int
)