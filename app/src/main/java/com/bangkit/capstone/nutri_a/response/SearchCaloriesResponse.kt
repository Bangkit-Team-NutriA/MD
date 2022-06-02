package com.bangkit.capstone.nutri_a.response

import com.google.gson.annotations.SerializedName

class SearchCaloriesResponse {
    @SerializedName("name")
    val name: String? = null

    @SerializedName("information")
    val informationCalories: InformationCalories? = null

    @SerializedName("status")
    val status: String? = null
}

class InformationCalories {
    @SerializedName("kalori")
    val kalori: String? = null

    @SerializedName("karbohidrat")
    val karbohidrat: String? = null

    @SerializedName("protein")
    val protein: String? = null

    @SerializedName("serving")
    val serving: String? = null

    @SerializedName("lemak")
    val lemak: String? = null
}