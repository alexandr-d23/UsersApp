package com.example.userlist.data.model


import com.google.gson.annotations.SerializedName


data class UserResponse(
    @SerializedName("about")
    val about: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("company")
    val company: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("eyeColor")
    val eyeColor: String,
    @SerializedName("favoriteFruit")
    val favoriteFruit: String,
    @SerializedName("friends")
    val friends: List<Friend>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("registered")
    val registered: String
) {
    data class Friend(
        @SerializedName("id")
        val id: Int
    )
}