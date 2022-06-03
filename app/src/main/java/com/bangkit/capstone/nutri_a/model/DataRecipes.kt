package com.bangkit.capstone.nutri_a.model

import android.os.Parcelable
import com.bangkit.capstone.nutri_a.response.Nutrisi
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DataRecipes(
    val Cara: List<String?>?,
    val Nama: String?,
    val nutrisi: @RawValue Nutrisi?,
    val Deskripsi: String?,
    val Bahan: List<String?>?,
    val Bahan2: List<String?>?,
    val Url: String?
) : Parcelable

@Parcelize
data class Nutrisi(

    @field:SerializedName("Energi (Energy)")
    val energy: String,

    @field:SerializedName("Karbohidrat (CHO)")
    val carbo: String,

    @field:SerializedName("Lemak (Fat)")
    val fat: String,

    @field:SerializedName("Protein (Protein)")
    val protein: String
) : Parcelable
