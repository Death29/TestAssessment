package com.example.testassessment.model.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseTrailerMovie(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItemTrailer>
) : Parcelable

@Parcelize
data class ResultsItemTrailer(

	@field:SerializedName("site")
	val site: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("official")
	val official: Boolean? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("published_at")
	val publishedAt: String? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("key")
	val key: String = ""
) : Parcelable
