package com.example.testapp1.di.data.module

import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.di.data.DataScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RemoteModule {

    @Provides
    @DataScope
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @DataScope
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsAPI.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @DataScope
    fun provideNewsApi(retrofit: Retrofit): NewsAPI = retrofit.create(NewsAPI::class.java)
}