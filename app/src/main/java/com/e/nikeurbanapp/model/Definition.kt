package com.e.nikeurbanapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Definition(

    @field:Json(name = "defid")
    val defid: Int = -1,

    @field:Json(name = "sound_urls")
    val soundUrls: List<String> = listOf(),

    @field:Json(name = "thumbs_down")
    val thumbsDown: Int? = null,

    @field:Json(name = "author")
    val author: String = "",

    @field:Json(name = "written_on")
    val writtenOn: String = "",

    @field:Json(name = "definition")
    val definition: String = "",

    @field:Json(name = "permalink")
    val permalink: String = "",

    @field:Json(name = "thumbs_up")
    val thumbsUp: Int? = null,

    @field:Json(name = "word")
    val word: String = "",

    @field:Json(name = "current_vote")
    val currentVote: String = "",

    @field:Json(name = "example")
    val example: String = ""
) : Parcelable