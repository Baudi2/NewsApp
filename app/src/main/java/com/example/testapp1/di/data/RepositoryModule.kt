package com.example.testapp1.di.data

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.repository.NewsRepository
import com.example.testapp1.data.repository.mapper.RemoteToLocalMapper
import org.koin.dsl.module

val repositoryModule = module {

    fun provideRemoteToLocalMapper(): RemoteToLocalMapper = RemoteToLocalMapper()

    fun provideRepository(
        api: NewsAPI,
        dao: ArticleDao,
        mapper: RemoteToLocalMapper
    ): NewsRepository {
        return NewsRepository(api, dao, mapper)
    }

    single { provideRemoteToLocalMapper() }
    single { provideRepository(api = get(), dao = get(), mapper = get()) }
}