package com.mazaady.domain.entity

data class MainCat(
    val id: Int,
    val name: String,
    val description: String,
    val slug: String,
    val children: List<Cat>,
)
