package com.bangkit.capstone.nutri_a.response

import com.google.gson.annotations.SerializedName

data class CalculatorResponse(

	@field:SerializedName("information")
	val information: Information? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Information(

	@field:SerializedName("carbo")
	val carbo: Double? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("fat")
	val fat: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)
