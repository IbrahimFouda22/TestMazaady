package com.mazaady.data.mapper

import com.mazaady.data.dto.OptionChildDto
import com.mazaady.domain.entity.OptionChild

fun OptionChildDto.toEntity() = OptionChild(
    id = id,
    name = name,
    slug = slug,
    options = options.map {
        it.toEntity()
    }
)