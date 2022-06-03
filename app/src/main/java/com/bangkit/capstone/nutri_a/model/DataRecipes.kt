package com.bangkit.capstone.nutri_a.model

import android.os.Parcelable
import com.bangkit.capstone.nutri_a.response.Nutrisi
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DataRecipes(
    val Nama: String?,
    val Url: String?
) : Parcelable

