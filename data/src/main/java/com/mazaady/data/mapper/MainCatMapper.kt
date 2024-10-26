package com.mazaady.data.mapper

import com.mazaady.data.dto.MainCatDtoData
import com.mazaady.domain.entity.Cat
import com.mazaady.domain.entity.MainCat

fun MainCatDtoData.toEntity() = MainCat(
    id = id,
    name = name ?: "",
    slug = slug ?: "",
    description = description ?: "",
    children = children.map { data ->
        Cat(
            id = data.id,
            name = data.name,
            slug = data.slug,
        )
    }
)