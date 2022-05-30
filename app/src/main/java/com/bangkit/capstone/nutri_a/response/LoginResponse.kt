package com.bangkit.capstone.nutri_a.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("token")
    val token: String

)

data class LoginResult(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)