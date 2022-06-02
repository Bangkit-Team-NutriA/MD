package com.bangkit.capstone.nutri_a.response

import com.google.gson.annotations.SerializedName

data class RecommendNonParamResponse(

	@field:SerializedName("information")
	val information: InformationNonParam? = null,

	@field:SerializedName("status")
	val status: String? = null
)


data class InformationNonParam(

	@field:SerializedName("siang")
	val siang: List<SiangItemNonParam?>? = null,

	@field:SerializedName("pagi")
	val pagi: List<PagiItemNonParam?>? = null,

	@field:SerializedName("malam")
	val malam: List<MalamItemNonParam?>? = null
)

data class PagiItemNonParam(

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

data class SiangItemNonParam(

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

data class MalamItemNonParam(

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
