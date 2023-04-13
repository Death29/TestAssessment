package com.example.testassessment.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGuestSession(

	@field:SerializedName("expires_at")
	val expiresAt: String? = null,

	@field:SerializedName("guest_session_id")
	val guestSessionId: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)
