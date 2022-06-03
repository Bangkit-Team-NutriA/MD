package com.bangkit.capstone.nutri_a.response

import com.google.gson.annotations.SerializedName

data class RecommendFoodResponse(

	@field:SerializedName("information")
	val information: InformationParam? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class InformationParam(

	@field:SerializedName("siang")
	val siang: List<SiangItemParam?>? = null,

	@field:SerializedName("pagi")
	val pagi: List<PagiItemParam?>? = null,

	@field:SerializedName("malam")
	val malam: List<MalamItemParam?>? = null
)

data class MalamItemParam(

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("Energi (Energy)")
	val energiEnergy: String? = null,

	@field:SerializedName("Karbohidrat (CHO)")
	val karbohidratCHO: String? = null,

	@field:SerializedName("Lemak (Fat)")
	val lemakFat: String? = null,

	@field:SerializedName("Protein (Protein)")
	val proteinProtein: String? = null
)

data class SiangItemParam(

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("Energi (Energy)")
	val energiEnergy: String? = null,

	@field:SerializedName("Karbohidrat (CHO)")
	val karbohidratCHO: String? = null,

	@field:SerializedName("Lemak (Fat)")
	val lemakFat: String? = null,

	@field:SerializedName("Protein (Protein)")
	val proteinProtein: String? = null
)

data class PagiItemParam(

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("Energi (Energy)")
	val energiEnergy: String? = null,

	@field:SerializedName("Karbohidrat (CHO)")
	val karbohidratCHO: String? = null,

	@field:SerializedName("Lemak (Fat)")
	val lemakFat: String? = null,

	@field:SerializedName("Protein (Protein)")
	val proteinProtein: String? = null
)
