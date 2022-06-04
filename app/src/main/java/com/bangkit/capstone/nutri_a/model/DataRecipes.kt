package com.bangkit.capstone.nutri_a.model

import android.os.Parcelable
import com.bangkit.capstone.nutri_a.response.Nutrisi
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DataRecipes(
    val cara: List<String?>?,
    val Nama: String?,
    val nutrisi: Nutrisi?,
    val deskripsi: String?,
    val bahan: List<String?>?,
    val bahan2: List<String?>?,
    val Url: String?
) : Parcelable

@Parcelize
data class DataNutrisi(
    val energiEnergy: String?,
    val lemakFat: String?,
    val karbohidratCHO: String?,
    val proteinProtein: String?
) : Parcelable

