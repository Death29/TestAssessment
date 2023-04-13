package com.example.testassessment.model.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseAllGenre(

	@field:SerializedName("genres")
	val genres: List<DataGenre>
) : Parcelable

@Parcelize
data class DataGenre(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable
