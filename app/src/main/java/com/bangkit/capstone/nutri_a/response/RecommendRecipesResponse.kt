package com.bangkit.capstone.nutri_a.response

import com.google.gson.annotations.SerializedName

data class RecommendRecipesResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Nutrisi(

	@field:SerializedName("Energi (Energy)")
	val energiEnergy: String? = null,

	@field:SerializedName("Lemak (Fat)")
	val lemakFat: String? = null,

	@field:SerializedName("Karbohidrat (CHO)")
	val karbohidratCHO: String? = null,

	@field:SerializedName("Protein (Protein)")
	val proteinProtein: String? = null
)
data class Item(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

)
data class DataItem(

	@field:SerializedName("Cara")
	val cara: List<String?>? = null,

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("nutrisi")
	val nutrisi: Nutrisi? = null,

	@field:SerializedName("Deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("Bahan")
	val bahan: List<String?>? = null,

	@field:SerializedName("Bahan2")
	val bahan2: List<String?>? = null,

	@field:SerializedName("Url")
	val url: String? = null
)
