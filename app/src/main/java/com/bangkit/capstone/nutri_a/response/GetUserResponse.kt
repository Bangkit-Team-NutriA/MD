package com.bangkit.capstone.nutri_a.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetUserResponse(

	@field:SerializedName("data")
	val data: DataUser? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Parcelize
data class DataUser(

	@field:SerializedName("jeniskelamin")
	val jeniskelamin: Boolean? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("olahraga")
	val olahraga: Int? = null,

	@field:SerializedName("berat")
	val berat: Int? = null,

	@field:SerializedName("tinggi")
	val tinggi: Int? = null,

	@field:SerializedName("tanggallahir")
	val tanggallahir: String? = null
) : Parcelable
