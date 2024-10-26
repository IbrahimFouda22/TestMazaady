package com.mazaady.data.remote.source

import com.mazaady.data.dto.BaseDto
import com.mazaady.data.dto.MainCatDto
import com.mazaady.data.dto.OptionChildDto
import com.mazaady.data.dto.PropertyDto


interface IRemoteDataSource {
    suspend fun getAllMainCats(): BaseDto<MainCatDto>
    suspend fun getProperties(catId: Int): BaseDto<List<PropertyDto>>
    suspend fun getOptionsChild(optionId: Int): BaseDto<List<OptionChildDto>>
}