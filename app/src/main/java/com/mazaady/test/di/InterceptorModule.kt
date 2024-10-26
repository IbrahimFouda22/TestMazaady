package com.mazaady.test.di

import com.mazaady.test.util.MyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {
    @Provides
    @Singleton
    fun provideInterceptor(): MyInterceptor {
        return MyInterceptor()
    }
}