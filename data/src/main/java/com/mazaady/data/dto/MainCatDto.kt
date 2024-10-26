package com.mazaady.data.dto

import com.google.gson.annotations.SerializedName

data class MainCatDto(
    @SerializedName("categories")
    val categories: List<MainCatDtoData>,
)

data class MainCatDtoData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("children")
    val children: List<CatDto>,
)
