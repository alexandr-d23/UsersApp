package com.example.userlist.domain.model

import org.joda.time.DateTime

data class User(
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
    val registered: DateTime,
    val friends: List<User>?
)