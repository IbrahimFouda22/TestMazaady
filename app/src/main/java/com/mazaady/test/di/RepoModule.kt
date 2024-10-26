package com.mazaady.test.di

import com.mazaady.data.remote.source.RemoteDataSource
import com.mazaady.data.repository.Repo
import com.mazaady.domain.repo.IRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(remoteDataSource: RemoteDataSource): IRepo {
        return Repo(remoteDataSource)
    }
}