package com.mazaady.data.dto

import com.google.gson.annotations.SerializedName

data class OptionDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
)