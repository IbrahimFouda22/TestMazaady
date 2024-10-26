package com.mazaady.data.dto

data class BaseDto<T>(
    val code: Int ,
    val message: String?,
    val data: T?,
)
