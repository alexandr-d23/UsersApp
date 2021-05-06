package com.example.userlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val about: String,
    val address: String,
    val age: Int,
    val company: String,
    val email: String,
    val eyeColor: String,
    val favoriteFruit: String,
    val isActive: Boolean,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone: String,
    val registered: String,
)