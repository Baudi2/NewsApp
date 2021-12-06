package com.example.testapp1.di.data

import com.example.testapp1.data.remote.api.NewsAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val remoteModule = module {

    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    }

    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsAPI.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    fun provideNewsApi(retrofit: Retrofit): NewsAPI = retrofit.create(NewsAPI::class.java)

    single { provideGsonConverterFactory() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(httpLoggingInterceptor = get()) }
    single { provideRetrofit(gsonConverterFactory = get(), okHttpClient = get()) }
    single { provideNewsApi(retrofit = get()) }
}


/*
@Module
class RemoteModule {

    @Provides
    @DataScope
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @DataScope
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    }

    @Provides
    @DataScope
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @DataScope
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsAPI.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @DataScope
    fun provideNewsApi(retrofit: Retrofit): NewsAPI = retrofit.create(NewsAPI::class.java)
}
 */