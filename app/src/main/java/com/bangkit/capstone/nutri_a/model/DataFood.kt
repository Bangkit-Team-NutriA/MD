package com.bangkit.capstone.nutri_a.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataFood(
    var name: String? = null,
    var calories: String? = null,
    var carbo: String? = null,
    var fat: String? = null,
    var protein: String? = null
) : Parcelable