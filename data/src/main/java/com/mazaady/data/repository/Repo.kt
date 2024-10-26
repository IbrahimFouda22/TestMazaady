package com.mazaady.data.repository

import com.mazaady.data.mapper.toEntity
import com.mazaady.data.remote.source.RemoteDataSource
import com.mazaady.domain.entity.MainCat
import com.mazaady.domain.repo.IRepo
import javax.inject.Inject

class Repo @Inject constructor(private val remoteDataSource: RemoteDataSource) : IRepo {
    override suspend fun getAllMainCats(): List<MainCat> {
        return remoteDataSource.getAllMainCats().data?.categories?.map {
            it.toEntity()
        } ?: emptyList()
    }

    override suspend fun getProperties(catId: Int) =
        remoteDataSource.getProperties(catId).data?.map {
            it.toEntity()
        } ?: emptyList()

    override suspend fun getOptionsChild(optionId: Int) =
        remoteDataSource.getOptionsChild(optionId).data?.map {
            it.toEntity()
        } ?: emptyList()
}