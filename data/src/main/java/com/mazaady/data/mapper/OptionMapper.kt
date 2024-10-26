package com.mazaady.data.mapper

import com.mazaady.data.dto.OptionDto
import com.mazaady.domain.entity.Option

fun OptionDto.toEntity() = Option(
    id = id,
    name = name,
    slug = slug
)