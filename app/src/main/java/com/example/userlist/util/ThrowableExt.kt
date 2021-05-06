package com.example.userlist.util

import android.content.res.Resources
import com.example.userlist.R
import java.net.UnknownHostException

fun Throwable.getErrorMessage(resources: Resources): String = when (this) {
    is UnknownHostException -> resources.getString(R.string.no_internet)
    else -> message ?: " "
}