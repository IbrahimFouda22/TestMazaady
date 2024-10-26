package com.mazaady.domain.usecase

import com.mazaady.domain.repo.IRepo
import javax.inject.Inject


class ManageCategoryUseCase @Inject constructor(private val repo: IRepo) {
    suspend fun getAllMainCats() = repo.getAllMainCats()
    suspend fun getProperties(catId: Int) = repo.getProperties(catId)
    suspend fun getOptionsChild(catId: Int) = repo.getOptionsChild(catId)
}