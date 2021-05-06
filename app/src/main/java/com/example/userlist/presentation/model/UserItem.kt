package com.example.userlist.presentation.model

data class UserItem(
    val id: Int,
    val email: String,
    val name: String,
    val isActive: Boolean,
)