package com.bangkit.capstone.nutri_a.response

import com.bangkit.capstone.nutri_a.model.InformationCalories
import com.google.gson.annotations.SerializedName

class SearchCaloriesResponse {
    @SerializedName("name")
    val name: String? = null

    @SerializedName("information")
    val informationCalories: InformationCalories? = null

    @SerializedName("status")
    val status: String? = null
}