package com.mazaady.domain.repo

import com.mazaady.domain.entity.MainCat
import com.mazaady.domain.entity.OptionChild
import com.mazaady.domain.entity.Property

interface IRepo {
    suspend fun getAllMainCats(): List<MainCat>
    suspend fun getProperties(catId: Int): List<Property>
    suspend fun getOptionsChild(optionId: Int): List<OptionChild>
}