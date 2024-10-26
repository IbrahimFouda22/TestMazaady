package com.mazaady.test.di

import com.mazaady.data.remote.source.IRemoteDataSource
import com.mazaady.data.remote.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource
}