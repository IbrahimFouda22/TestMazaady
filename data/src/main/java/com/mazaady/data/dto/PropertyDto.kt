package com.mazaady.data.dto

import com.google.gson.annotations.SerializedName

data class PropertyDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("options")
    val options: List<OptionDto>,
)