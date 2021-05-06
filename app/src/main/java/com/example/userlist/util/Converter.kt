package com.example.userlist.util

import com.example.userlist.data.model.UserModel
import com.example.userlist.data.model.UserResponse
import com.example.userlist.data.room.relations.UserWithFriends
import com.example.userlist.domain.model.User
import org.joda.time.format.ISODateTimeFormat

fun userModelToUser(userModel: UserModel): User =
    User(
        id = userModel.id,
        about = userModel.about,
        address = userModel.address,
        isActive = userModel.isActive,
        email = userModel.email,
        name = userModel.name,
        age = userModel.age,
        company = userModel.company,
        eyeColor = userModel.eyeColor,
        favoriteFruit = userModel.favoriteFruit,
        friends = null,
        latitude = userModel.latitude,
        longitude = userModel.longitude,
        phone = userModel.phone,
        registered = fromIsoToDateTime(userModel.registered)
    )

fun userModelWithFriendsToUser(userWithFriends: UserWithFriends): User {
    val userModel = userWithFriends.user
    return User(
        id = userModel.id,
        about = userModel.about,
        address = userModel.address,
        isActive = userModel.isActive,
        email = userModel.email,
        name = userModel.name,
        age = userModel.age,
        company = userModel.company,
        eyeColor = userModel.eyeColor,
        favoriteFruit = userModel.favoriteFruit,
        friends = userWithFriends.friends.map(::userModelToUser),
        latitude = userModel.latitude,
        longitude = userModel.longitude,
        phone = userModel.phone,
        registered = fromIsoToDateTime(userModel.registered)
    )
}

fun userResponseToUserModel(userResponse: UserResponse): UserModel =
    UserModel(
        id = userResponse.id,
        about = userResponse.about,
        address = userResponse.address,
        isActive = userResponse.isActive,
        email = userResponse.email,
        name = userResponse.name,
        age = userResponse.age,
        company = userResponse.company,
        eyeColor = userResponse.eyeColor,
        favoriteFruit = userResponse.favoriteFruit,
        latitude = userResponse.latitude,
        longitude = userResponse.longitude,
        phone = userResponse.phone,
        registered = userResponse.registered
    )

fun fromIsoToDateTime(datiTime: String) = ISODateTimeFormat.dateTimeParser()
    .withOffsetParsed()
    .parseDateTime(
        datiTime.split(" ")
            .toList().reduce { s1, s2 -> s1 + s2 }
    )