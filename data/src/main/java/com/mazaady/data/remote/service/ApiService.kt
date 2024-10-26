package com.mazaady.data.remote.service

import com.mazaady.data.dto.BaseDto
import com.mazaady.data.dto.MainCatDto
import com.mazaady.data.dto.OptionChildDto
import com.mazaady.data.dto.PropertyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("get_all_cats")
    suspend fun getAllMainCats():Response<BaseDto<MainCatDto>>

    @GET("properties")
    suspend fun getProperties(
        @Query("cat") catId:Int
    ):Response<BaseDto<List<PropertyDto>>>

    @GET("get-options-child/{optionId}")
    suspend fun getOptionsChild(
        @Path("optionId") optionId:Int
    ):Response<BaseDto<List<OptionChildDto>>>
}