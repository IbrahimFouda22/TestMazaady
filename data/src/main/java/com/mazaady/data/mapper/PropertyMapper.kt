package com.mazaady.data.mapper

import com.mazaady.data.dto.PropertyDto
import com.mazaady.domain.entity.Property

fun PropertyDto.toEntity() = Property(
    id = id,
    name = name,
    slug = slug,
    options = options.map {
        it.toEntity()
    }
)