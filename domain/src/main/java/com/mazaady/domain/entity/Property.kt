package com.mazaady.domain.entity


data class Property(
    val id: Int,
    val name: String,
    val slug: String,
    val options: List<Option>,
)