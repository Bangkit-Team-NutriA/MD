package com.bangkit.capstone.nutri_a.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val token: String,
    val isLogin: Boolean
) : Parcelable
