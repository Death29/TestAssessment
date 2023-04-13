package com.example.testassessment.model.response

import com.google.gson.annotations.SerializedName

data class ResponseLoginSession(

	@field:SerializedName("expires_at")
	val expiresAt: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("request_token")
	val requestToken: String? = null
)
